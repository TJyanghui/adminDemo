package com.sge.clear.admin.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.sge.clear.admin.dao.ClearStepDOMapper;
import com.sge.clear.admin.model.ClearStepDO;
import com.sge.clear.admin.utils.Utils;

/**
 * @Desc 清算单步执行服务，自实现了调度服务，通过Process执行进程的方式实现拉起清算模块
 */
public class StepDebugServiceImpl implements StepDebugService {
	
	private static final Logger logger = LoggerFactory.getLogger("file.log");
	private static final Logger clear_logger = LoggerFactory.getLogger("clear.log");
	
	@Resource(name = "clearStepDOMapper")
	private ClearStepDOMapper clearStepDOMapper;

	@Resource(name = "asyncExecutor")
	private ThreadPoolTaskExecutor asyncExecutor; // spring线程池，用来执行异步操作
	
	private List<ClearStepDO> stepDOList;
	
	private StringBuffer logBuffer=new StringBuffer();

	@Override
	public List<ClearStepDO> loadStep() {
		stepDOList = clearStepDOMapper.getAll();
		return stepDOList;
	}

	@Override
	public List<ClearStepDO> getStepStatus() {
		return stepDOList;
	}

	@Override
	public List<ClearStepDO> runStep(final String clearDate,final int step) {
		logger.info("StepDebugService.runStep,date={},step={}", clearDate, step);
		logBuffer.setLength(0);
		// 异步执行指定的清算步骤
		asyncExecutor.execute(new Runnable() {
			public void run() {
				executeStep(clearDate,step);
			}
		});
		
		logger.info("asyncExecutor execute:executeStep(clearDate,step);" );
		return this.stepDOList;
	}
	
	/**
	 * @Description 		执行清算步骤的具体实现(通过Runtime.exec执行)
	 * @param step 			清算步骤号
	 * @return 				是否执行成功执行
	 */
	private int executeStep(String clearDate,int step){
		logBuffer.append("清算场次，date=" + clearDate + ",step=" + step + "开始执行:\n");
		logger.info("clearStep,date={},step={} begin", clearDate, step);
		clear_logger.info("clearStep,date={},step={} begin", clearDate, step);
		long stepStartTime = System.currentTimeMillis();
		ClearStepDO clearStep = this.getClearStepByStep(step);
		clearStep.setStatus("Runing");
		logBuffer.append("清算场次,date=" + clearDate + ",step=" + step + "执行中....:\n");
		String cmd="";
		//执行清算步骤
		try {
			// 如果是本地上的模块，则通过Runtime调用
			if ("localhost".equals(clearStep.getIp().trim().toLowerCase())) {
				Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
				cmd = clearStep.getUrl().replace("${CUR_DATE}", clearDate); //0711临时需求，实现参数模版替换，先硬编码实现
				Process p = run.exec(cmd);// 启动另一个进程来执行命令
				BufferedInputStream in = new BufferedInputStream(p.getInputStream());
				BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
				BufferedInputStream err = new BufferedInputStream(p.getErrorStream());
				BufferedReader errBr = new BufferedReader(new InputStreamReader(err));
				String lineStr="";
				String lineErr="";
				while ((lineStr = inBr.readLine()) != null)
					// 获得命令执行后在控制台的输出信息
					logBuffer.append(lineStr);// 打印输出信息
				while ((lineErr = errBr.readLine()) != null)
					// 获得命令执行后在控制台的输出信息
					clear_logger.info(lineErr);
				// 检查命令是否执行失败。
				int processRet=0;
				if (p.waitFor() != 0) {
					processRet = p.exitValue();
					if (processRet != 0)// p.exitValue()==0表示正常结束，1：非正常结束
						logBuffer.append("清算场次执行失败，执行命令" + cmd + ",退出值:" + processRet);
				}
				
				inBr.close();
				in.close();
				long stepConsumeTime = System.currentTimeMillis()-stepStartTime;
				clearStep.setStatus(processRet==0?"Success":"Fail");
				logBuffer.append("清算场次,date=" + clearDate + ",step=" + step + "执行结束,stepConsumeTime=" + stepConsumeTime + " StepFinished \n");
				logger.info("clearTask finished,date={},ProcessRet={},stepConsumeTime={}", clearDate, processRet, stepConsumeTime);
				clear_logger.info("clearTask finished,date={},ProcessRet={},stepConsumeTime={}", clearDate, processRet, stepConsumeTime);
				
				return processRet;
			}
			// 远程模块，通过HTTP接口方式调用
			else {
				// TODO
				return 0;
			}
		} catch (Exception e) {
			logBuffer.append("清算场次执行异常，场次应用:" + cmd + ",异常信息:" + e.getLocalizedMessage() + "\n");
			clearStep.setStatus("Fail");
			logger.error("clearStep,date={},step={},excepting={}", clearDate, step, e);
			clear_logger.error("clearStep,date={},step={},excepting={}", clearDate, step, e);
			return -1;
		} 		
//		try {
//			logBuffer.append("清算场次,date=" + clearDate + ",step=" + step + "执行中....:\n");
//			Thread.sleep(5000);
//			long stepConsumeTime = System.currentTimeMillis()-stepStartTime;
//			logBuffer.append("清算场次,date=" + clearDate + ",step=" + step + "执行结束,stepConsumeTime=" + stepConsumeTime + " StepFinished \n");
//			logger.info("clearStep,date=" + clearDate + ",step=" + step + " finished,stepConsumeTime=" + stepConsumeTime);
//			clearStep.setStatus("Success");
//			return 0;
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			logger.error("clearStep,date=" + clearDate + ",step=" + step + " exception,msg=" + e.getStackTrace());
//			return -1;
//		}
	}
	
	@Override
	public String getTaskLog() {
		return this.logBuffer.toString();
	}
	
	/**
	 * @Description 根据序号step查找对应的ClearStepDO
	 * @param step
	 * @return 对应的ClearStepDO
	 */
	private ClearStepDO getClearStepByStep(int step){
		for(ClearStepDO stepDO:stepDOList) {
			if(stepDO.getStep() == step)
				return stepDO;
		}
		return null;
	}
	
	@Override
	public String getStepLog(String clearDate, int step, int lines) {	
		ClearStepDO clearStep = this.getClearStepByStep(step);
		String fileName = clearStep.getLogfile().replace("${CUR_DATE}", clearDate); //0711临时需求，实现参数模版替换，先硬编码实现
		String result = "#tail -n" + lines + " " + fileName + "\n";
		return result + Utils.readLastNLines(fileName, lines);
	}

}
