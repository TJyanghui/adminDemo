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
 * @Desc 清算整体任务服务，通过调度服务的远程接口(HTTP/JSON)实现
 */
public class StepServiceHttpImpl implements StepService {
	
	private static final Logger logger = LoggerFactory.getLogger("file.log");
	
	private static final String LOAD_STEP_KEY = "LOAD_STEP_URL";
	private static final String RUN_TASK_KEY = "RUN_TASK_URL";
	private static final String CONT_TASK_KEY = "CONT_TASK_URL";
	private static final String GET_STATUS_KEY = "GET_STATUS_URL";
	private static final String IS_TASK_RUNNING_KEY = "IS_TASK_RUNNING_URL";
	private static final String GET_TASK_LOG_KEY = "GET_TASK_LOG_URL";
	private static final String GET_STEP_LOG_KEY = "GET_STEP_LOG_URL";
	
	
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
		logger.info("StepServiceHttpImpl.loadStep");
		String url = urlMap.getProperty(LOAD_STEP_KEY);
		JSONObject jsonObject = Utils.getJSONObjectByGet(httpClient,url);
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
		logger.info("StepServiceHttpImpl.getStepStatus");
		String url = urlMap.getProperty(GET_STATUS_KEY);
		JSONObject jsonObject = Utils.getJSONObjectByGet(httpClient,url);
		int retCode = jsonObject.getInt(KEY_RETCODE);
		if (retCode != 0) {
			return null;
		}

		JSONArray jsonArray = jsonObject.getJSONArray(KEY_REDATA);
		List<ClearStepDO> steps = Utils.JSONArrayToClearStepList(jsonArray);
		return steps;
	}

	/**
	 * @Description 从第一个步骤开始，异步执行清算步骤
	 */
	@Override
	public int runTask(String clearDate) {
		logger.info("StepServiceHttpImpl.runTask({})", clearDate);
		String url = urlMap.getProperty(RUN_TASK_KEY);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("date", clearDate));
		JSONObject jsonObject = Utils.getJsonObjectByPost(httpClient, url, params);
		
		int retCode = jsonObject.getInt(KEY_RETCODE);
		if (retCode != 0) {
			return -1;
		}

		return jsonObject.getInt(KEY_REDATA);
		
	}

	/**
	 * @Description 从步骤beginStep开始，异步执行清算步骤
	 * @param beginStep 开始执行的最小步骤
	 * @return 返回清算步骤的状态(主要是status字段，标识目前清算的执行情况)
	 */
	@Override
	public List<ClearStepDO> contTask(final int beginStep) {
		logger.info("StepServiceHttpImpl.contTask({})",beginStep);
		String url = urlMap.getProperty(CONT_TASK_KEY);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("step", String.format("%d", beginStep)));
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
	public boolean isTaskRuning() {
		logger.info("StepServiceHttpImpl.isTaskRuning");
		String url = urlMap.getProperty(IS_TASK_RUNNING_KEY);
		JSONObject jsonObject = Utils.getJSONObjectByGet(httpClient, url);
		int retCode = jsonObject.getInt(KEY_RETCODE);
		if (retCode != 0) {
			return false;
		}
		
		return jsonObject.getBoolean(KEY_REDATA);
	}

	@Override
	public String getTaskLog() {
		logger.info("StepServiceHttpImpl.getTaskLog");
		String url = urlMap.getProperty(GET_TASK_LOG_KEY);
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
	public String getStepLog(int step, int lines) {
		logger.info("StepServiceHttpImpl.getStepLog({},{})",step,lines);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("step", String.format("%d", step)));
		params.add(new BasicNameValuePair("lines", String.format("%d", lines)));
		
		String url = urlMap.getProperty(GET_STEP_LOG_KEY);
		JSONObject jsonObject = Utils.getJsonObjectByPost(httpClient, url, params);
		int retCode = jsonObject.getInt(KEY_RETCODE);
		if (retCode != 0) {
			return "";
		}
		String retData = jsonObject.getString(KEY_REDATA);
		
		return retData;
	}

}
