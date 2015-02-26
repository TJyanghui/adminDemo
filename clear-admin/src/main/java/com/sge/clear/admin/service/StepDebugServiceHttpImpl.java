package com.sge.clear.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sge.clear.admin.model.ClearStepDO;
import com.sge.clear.admin.utils.Utils;

/**
 * @Desc 清算单步执行服务，通过调度服务的远程接口(HTTP/JSON)实现
 */
public class StepDebugServiceHttpImpl implements StepDebugService {
	
	private static final Logger logger = LoggerFactory.getLogger("file.log");
	
	private static final String DEBUG_LOAD_STEP_KEY = "DEBUG_LOAD_STEP_URL";
	private static final String DEBUG_RUN_STEP_KEY = "DEBUG_RUN_STEP_URL";
	private static final String DEBUG_GET_STATUS_KEY = "DEBUG_GET_STATUS_URL";
	private static final String DEBUG_GET_TASK_LOG_KEY = "DEBUG_GET_TASK_LOG_URL";
	private static final String DEBUG_GET_STEP_LOG_KEY = "DEBUG_GET_STEP_LOG_URL";
	
	
	//json中的两个key，分别表示返回状态和返回数据。retCode=0时retData有效。
	private final String KEY_RETCODE = "retCode";
	private final String KEY_REDATA = "retData";

	private HttpClient httpClient;
	
	private Properties urlMap;
	
	public Properties getUrlMap() {
		return urlMap;
	}

	public void setUrlMap(Properties urlMap) {
		this.urlMap = urlMap;
	}

	public void init() {
		httpClient = HttpClients.createDefault();
	}

	@Override
	public List<ClearStepDO> loadStep() {
		logger.info("StepDebugServiceHttpImpl.loadStep");
		String url = urlMap.getProperty(DEBUG_LOAD_STEP_KEY);
		JSONObject jsonObject = Utils.getJSONObjectByGet(httpClient, url);
		int retCode = jsonObject.getInt(KEY_RETCODE);
		if (retCode != 0) {
			return null;
		}

		JSONArray jsonArray = jsonObject.getJSONArray(KEY_REDATA);
		List<ClearStepDO> steps = Utils.JSONArrayToClearStepList(jsonArray);
		return steps;

	}
	
	@Override
	public List<ClearStepDO> runStep(String clearDate, int step) {
		logger.info("StepDebugServiceHttpImpl.runStep({},{})",clearDate,step);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("date", clearDate));
		params.add(new BasicNameValuePair("step", String.format("%d", step)));

		
		String url = urlMap.getProperty(DEBUG_RUN_STEP_KEY);
		JSONObject jsonObject = Utils.getJsonObjectByPost(httpClient, url, params);
		int retCode = jsonObject.getInt(KEY_RETCODE);
		if (retCode != 0) {
			return null;
		}

		JSONArray jsonArray = jsonObject.getJSONArray(KEY_REDATA);
		List<ClearStepDO> steps = Utils.JSONArrayToClearStepList(jsonArray);
		return steps;
	}

	@Override
	public List<ClearStepDO> getStepStatus() {
		logger.info("StepDebugServiceHttpImpl.getStepStatus");
		String url = urlMap.getProperty(DEBUG_GET_STATUS_KEY);
		JSONObject jsonObject = Utils.getJSONObjectByGet(httpClient, url);
		int retCode = jsonObject.getInt(KEY_RETCODE);
		if (retCode != 0) {
			return null;
		}

		JSONArray jsonArray = jsonObject.getJSONArray(KEY_REDATA);
		List<ClearStepDO> steps = Utils.JSONArrayToClearStepList(jsonArray);
		return steps;
	}



	@Override
	public String getTaskLog() {
		logger.info("StepDebugServiceHttpImpl.getTaskLog");
		String url = urlMap.getProperty(DEBUG_GET_TASK_LOG_KEY);
		JSONObject jsonObject = Utils.getJSONObjectByGet(httpClient, url);
		int retCode = jsonObject.getInt(KEY_RETCODE);
		if (retCode != 0) {
			return "";
		}
		
		return jsonObject.getString(KEY_REDATA);
	}


	@Override
	/**
	 * @Description 返回清算步骤明细日志内容
	 * @param step  清算步骤号，根据此步骤号得到日志路径
	 * @param lines 读取的日志行数
	 */
	public String getStepLog(String clearDate, int step, int lines) {
		logger.info("StepDebugServiceHttpImpl.getStepLog({},{},{})",clearDate,step,lines);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("clearDate", clearDate));
		params.add(new BasicNameValuePair("step", String.format("%d", step)));
		params.add(new BasicNameValuePair("lines", String.format("%d", lines)));
		
		String url = urlMap.getProperty(DEBUG_GET_STEP_LOG_KEY);
		JSONObject jsonObject = Utils.getJsonObjectByPost(httpClient, url, params);
		int retCode = jsonObject.getInt(KEY_RETCODE);
		if (retCode != 0) {
			return "";
		}
		String retData = jsonObject.getString(KEY_REDATA);
		
		return retData;
	}

}
