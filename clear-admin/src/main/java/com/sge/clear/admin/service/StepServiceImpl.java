package com.sge.clear.admin.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.sge.clear.admin.dao.ClearStepDOMapper;
import com.sge.clear.admin.model.ClearStepDO;
import com.sge.clear.admin.utils.Utils;

/**
 * @Desc 清算整体任务服务，自实现了调度服务，通过Process执行进程的方式实现拉起清算模块
 */
public class StepServiceImpl implements StepService {
	
	private static final Logger logger = LoggerFactory.getLogger("file.log");
	private static final Logger clear_logger = LoggerFactory.getLogger("clear.log");

	@Resource(name = "clearStepDOMapper")
	private ClearStepDOMapper clearStepDOMapper;

	@Resource(name = "asyncExecutor")
	private ThreadPoolTaskExecutor asyncExecutor; 			// spring线程池，用来执行异步操作

	private List<ClearStepDO> stepDOList;

	private boolean isTaskRunning = false;

	private String clearDate = "";

	private StringBuffer logBuffer = new StringBuffer();  	//用于页面显示的日志信息

	@Override
	public List<ClearStepDO> loadStep() {
		stepDOList = clearStepDOMapper.getEnable();
		return stepDOList;
	}

	@Override
	public List<ClearStepDO> getStepStatus() {
		return stepDOList;
	}

	/**
	 * @Description 从第一个步骤开始，异步执行清算步骤
	 */
	@Override
	public int runTask(String clearDate) {
		logger.info("StepService.runTask({})", clearDate);
		// TODO 前置条件检查

		// 设置清算日期
		this.clearDate = clearDate;
		logBuffer.setLength(0); // 清空日志缓存
		// 异步执行清算步骤
		asyncExecutor.execute(new Runnable() {
			public void run() {
				executeTask(0);
			}
		});
		logger.info("asyncExecutor execute:executeTask(0);" );

		isTaskRunning = true;
		return 0;
	}

	/**
	 * @Description 从步骤beginStep开始，异步执行清算步骤
	 * @param beginStep 开始执行的最小步骤
	 * @return 返回清算步骤的状态(主要是status字段，标识目前清算的执行情况)
	 */
	@Override
	public List<ClearStepDO> contTask(final int beginStep) {
		logger.info("StepService.contTask({})",beginStep);
		// 异步执行清算步骤
		asyncExecutor.execute(new Runnable() {
			public void run() {
				executeTask(beginStep);
			}
		});
		
		logger.info("asyncExecutor execute:executeTask({})", beginStep);

		isTaskRunning = true;
		return this.stepDOList;
	}

	/**
	 * @Description 串行执行清算步骤
	 * @param beginStep 开始执行的最小步骤
	 */
	private void executeTask(int beginStep) {
		logBuffer.append("清算任务开始执行,清算日期" + this.clearDate + ":beginStep=" + beginStep + ":\n");
		logger.info("clearTask begin,date={},beginStep={}",this.clearDate, beginStep);
		clear_logger.info("clearTask begin,date={},beginStep={}",this.clearDate, beginStep);
		long taskStartTime = System.currentTimeMillis();
		int len = stepDOList.size();
		Collections.sort(stepDOList);
		// 按步骤依次执行
		for (int i = 0; i < len; i++) {
			// 跳过beginStep之前的步骤，继续执行
			if (stepDOList.get(i).getStep() < beginStep)
				continue;
			// 执行步骤
			setStepStatus(i + 1, "Waiting"); // 将beginStep之后的步骤设为等待执行
			logBuffer.append("清算场次" + stepDOList.get(i).getName() + "开始执行...\n");
			logger.info("step:{} begin...",stepDOList.get(i).getName());
			clear_logger.info("step:{} begin...",stepDOList.get(i).getName());
			long stepStartTime = System.currentTimeMillis();
			stepDOList.get(i).setStatus("Runing");
			int ret = executeStep(stepDOList.get(i));
			long stepConsumeTime = System.currentTimeMillis()-stepStartTime;
			if (ret == 0) {
				stepDOList.get(i).setStatus("Success");
				logBuffer.append("清算场次" + stepDOList.get(i).getName() + "执行成功,耗时=" + stepConsumeTime +"\n");
				logger.info("step:{} succeed,stepConsumeTime={}", stepDOList.get(i).getName(), stepConsumeTime);
				clear_logger.info("step:{} succeed,stepConsumeTime={}", stepDOList.get(i).getName(), stepConsumeTime);
			} else {
				stepDOList.get(i).setStatus("Fail");
				setStepStatus(i + 1, "Cancel");
				logBuffer.append("清算场次" + stepDOList.get(i).getName() + "执行失败,耗时=" + stepConsumeTime +"\n");
				logger.info("step:{} failed,stepConsumeTime={}", stepDOList.get(i).getName(), stepConsumeTime);
				clear_logger.info("step:{} failed,stepConsumeTime={}", stepDOList.get(i).getName(), stepConsumeTime);
				break;
			}
		}
		long taskConsumeTime = System.currentTimeMillis()-taskStartTime;
		logBuffer.append("清算任务执行结束.清算日期" + this.clearDate + "总耗时=" + taskConsumeTime +"\n");
		logger.info("clearTask finished,date={},taskConsumeTime={}", this.clearDate, taskConsumeTime);
		clear_logger.info("clearTask finished,date={},taskConsumeTime={}", this.clearDate, taskConsumeTime);
		isTaskRunning = false;
	}

	/**
	 * @Description 执行清算步骤的具体实现(通过Runtime.exec执行)
	 * @param clearStep 清算步骤
	 * @return 是否执行成功执行
	 */
	private int executeStep(ClearStepDO clearStep) {
		String cmd = "";
		try {
			// 如果是本地上的模块，则通过Runtime调用
			if ("localhost".equals(clearStep.getIp().trim().toLowerCase())) {
				Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
				cmd = clearStep.getUrl().replace("${CUR_DATE}", this.clearDate);  //0711临时需求，实现参数模版替换，先硬编码实现
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
				if (p.waitFor() != 0) {
					if (p.exitValue() != 0)// p.exitValue()==0表示正常结束，1：非正常结束
						logBuffer.append("清算场次执行失败，场次路径" + cmd + ",退出值:" + p.exitValue());
				}
				inBr.close();
				in.close();
				return p.exitValue();
			}
			// 远程模块，通过HTTP接口方式调用
			else {
				// TODO
				return 0;
			}
		} catch (Exception e) {
			logBuffer.append("清算场次执行异常，场次路径:" + cmd + ",异常信息:" + e.getLocalizedMessage() + "\n");
			return -1;
		}
	}

	@Override
	public boolean isTaskRuning() {
		return this.isTaskRunning;
	}

	@Override
	public String getTaskLog() {
		return this.logBuffer.toString();
	}

	/**
	 * @Description 批量修改步骤状态(主要用于某步骤失败后，后续步骤都设为取消;重试后，后续步骤都设为等待)
	 * @param begIndex
	 *            修改begIndex以后的步骤状态
	 * @param status
	 *            修改后的步骤状态
	 */
	private void setStepStatus(int begIndex, String status) {
		for (int i = begIndex; i < stepDOList.size(); i++) {
			stepDOList.get(i).setStatus(status);
		}
	}

	@Override
	/**
	 * @Description 返回清算步骤明细日志内容
	 * @param step  清算步骤号，根据此步骤号得到日志路径
	 * @param lines 读取的日志行数
	 */
	public String getStepLog(int step, int lines) {
		ClearStepDO clearStep = this.getClearStepByStep(step);
		String fileName = clearStep.getLogfile().replace("${CUR_DATE}", this.clearDate); //0711临时需求，实现参数模版替换，先硬编码实现
		String result = "#tail -n " + lines + " " + fileName + "\n";
		return result + Utils.readLastNLines(fileName, lines);
	}

	/**
	 * @Description 根据序号step查找对应的ClearStepDO
	 * @param step
	 * @return 对应的ClearStepDO
	 */
	private ClearStepDO getClearStepByStep(int step) {
		for (ClearStepDO stepDO : stepDOList) {
			if (stepDO.getStep() == step)
				return stepDO;
		}
		return null;
	}

}
