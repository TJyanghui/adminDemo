package com.sge.clear.admin.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sge.clear.admin.model.ClearStepDO;

public class Utils {
	
	private static final Logger logger = LoggerFactory.getLogger("file.log");
	
	// utf-8编码名称
	private static final String UTF_8 = "utf-8";
	
	/**
	 * @Description 读取文件filename的最后N行
	 * @param fileName
	 * @param lines
	 * @return
	 */
	public static String readLastNLines(String fileName,int lines){
		StringBuffer logBuffer=new StringBuffer();
		try{
			Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象  
	        Process p = run.exec("tail -n " + lines +" " +fileName);// 启动另一个进程来执行命令 ,不能加-f，否则会导致下面的while死循环
	        BufferedInputStream in = new BufferedInputStream(p.getInputStream());  
	        BufferedReader inBr = new BufferedReader(new InputStreamReader(in));  
	        String lineStr;  
	        while ((lineStr = inBr.readLine()) != null)  
	            //获得命令执行后在控制台的输出信息  
	        	logBuffer.append(lineStr);// 打印输出信息  
	        //检查命令是否执行失败。  
	        if (p.waitFor() != 0) {  
	            if (p.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束  
	            	logBuffer.append("命令执行失败!");  
	        }  
	        inBr.close();  
	        in.close();  
	        return logBuffer.toString();
		}
		catch (Exception e) {    
        	e.printStackTrace(); 
        	return "读取日志文件出错";
        }  
	}

	/**
	 * @Description 根据可执行文件的后缀名拼接执行命令 eg: test.py ——> python test.py
	 * @param cmd 
	 * @return
	 */
	public static String getExecCmd(String cmd){
		int idx = cmd.lastIndexOf(".");
		if(idx < 0)   //非可执行文件，直接返回
			return cmd;
		String prefix=cmd.substring(idx+1);
		if("py".equals(prefix))
			return "python "+ cmd;
		if("sh".equals(prefix))
			return "sh "+ cmd;
		
		return cmd;
	}
	
	public static boolean isEmpty(String abc) {
		return abc == null || abc.trim().equals("");
	}
	
	public static JSONObject getJSONObjectByGet(HttpClient httpClient,String uriString) {

		if ("".equals(uriString) || uriString == null) {
			logger.warn("uriString is null!");
			return null;
		}		
		// 利用URL生成一个HttpGet请求
		HttpGet httpGet = new HttpGet(uriString);
		JSONObject jsonObject = getJsonObjectBy(httpClient,httpGet);

		return jsonObject;
	}
	
	public static JSONObject getJsonObjectByPost(HttpClient httpClient,String uriString,List<NameValuePair> params) {
		if ("".equals(uriString) || uriString == null) {
			logger.warn("uriString is null!");
			return null;
		}
		
		HttpPost httpPost = new HttpPost(uriString);
		if (params != null) {
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(params, UTF_8));
			} catch (UnsupportedEncodingException e) {
				logger.error("非法的请求格式"+ UTF_8,e);
				throw new IllegalStateException("非法的请求格式"+UTF_8);
			}
		}
		
		JSONObject jsonObject = getJsonObjectBy(httpClient,httpPost);
		return jsonObject;
	}
	
	public static JSONObject getJsonObjectBy(HttpClient httpClient,HttpUriRequest httpMethod) {
		String uriString = httpMethod.getURI().toString();
		HttpResponse httpResponse = null;
		StringBuilder entityStringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		JSONObject resultJsonObject = null;
		
		try {
			// HttpClient发出一个HttpGet请求
			httpResponse = httpClient.execute(httpMethod);
		} catch (IOException e) {
			logger.error("地址[" + uriString + "]无法访问", e);
			throw new IllegalStateException("地址[" + uriString + "]无法访问");
		}

		// 得到httpResponse的状态响应码
		int statusCode = httpResponse.getStatusLine().getStatusCode();
				
		if (statusCode == HttpStatus.SC_OK) {
			// 得到httpResponse的实体数据
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				try {
					bufferedReader = new BufferedReader(new InputStreamReader(
							httpEntity.getContent(), "UTF-8"), 8 * 1024);
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						entityStringBuilder.append(line + "/n");
					}
					
					// 利用从HttpEntity中得到的String生成JsonObject
					resultJsonObject = new JSONObject(entityStringBuilder.toString());
				} catch (JSONException e) {
					logger.error("无法生成JSONObject对象", e);
					throw new IllegalStateException("无法生成JSONObject对象:"
							+ e.getMessage());
				} catch (UnsupportedEncodingException e) {
					String errMsg = "从地址[" + uriString + "]获得的字符串不是有效的UTF-8格式";
					logger.error(errMsg, e);
					throw new IllegalStateException(errMsg);
				} catch (IOException e) {
					logger.error("无法buffer中读取数据", e);
					throw new IllegalStateException("无法buffer中读取数据");
				}
			}
		}
		
		if (statusCode == HttpStatus.SC_NOT_FOUND) {
			String errMsg = "404:Page not found["+uriString+"]";
			logger.error(errMsg);
			throw new IllegalStateException(errMsg);
		}

		return resultJsonObject;
	}

	public static ClearStepDO JSONObjectToClearStep(JSONObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}

		Long gmtCreated = jsonObject.getLong("gmtCreated");
		Long gmtModified = jsonObject.getLong("gmtModified");
		
		Date created_at = new Date(gmtCreated);
		Date updated_at = new Date(gmtModified);
		
		ClearStepDO step = new ClearStepDO();
		step.setStep(jsonObject.getInt("step"));
		step.setName(jsonObject.getString("name"));
		step.setIp(jsonObject.getString("ip"));
		step.setUrl(jsonObject.getString("url"));
		step.setLogfile(jsonObject.isNull("logfile")?"" : jsonObject.getString("logfile"));
		step.setParam(jsonObject.isNull("param") ? "" : jsonObject
				.getString("param"));
		step.setIsEnable(jsonObject.getInt("isEnable"));
		step.setComt(jsonObject.getString("comt"));
		step.setGmtCreated(created_at);
		step.setGmtModified(updated_at);
		step.setStatus(jsonObject.getString("status"));
		return step;
	}

	public static List<ClearStepDO> JSONArrayToClearStepList(JSONArray jsonArray) {
		List<ClearStepDO> steps = new ArrayList<ClearStepDO>();
		if (jsonArray != null) {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				ClearStepDO step = JSONObjectToClearStep(jsonObject);
				steps.add(step);
			}
		}
		return steps;
	}
}
