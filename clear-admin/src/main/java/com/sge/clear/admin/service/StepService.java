package com.sge.clear.admin.service;

import java.util.List;

import com.sge.clear.admin.model.ClearStepDO;


/**
 * @Description 清算整体任务服务类
 * @author yh 2014-05-14
 */
public interface StepService {
    /** 
     * @Description 加载清算步骤配置
     */
	public List<ClearStepDO> loadStep();
	
    /** 
     * @Description 返回清算步骤的状态(主要是status字段，标识目前清算的执行情况)
     */
	public List<ClearStepDO> getStepStatus();
	
    /** 
     * @Description 执行清算任务
     */
	public int runTask(String clearDate);
	
	/**
	 * @Description 从步骤beginStep开始，异步执行清算步骤
	 * @param beginStep 开始执行的最小步骤
	 * @return 返回清算步骤的状态(主要是status字段，标识目前清算的执行情况)
	 */
	public List<ClearStepDO> contTask(int beginStep);
	
	
    /** 
     * @Description 是否有清算任务正在执行
     */
	public boolean isTaskRuning();
	
    /** 
     * @Description 返回清算任务整体日志(此处返回的日志是异步执行后，标准输出返回的日志)
     */
	public String getTaskLog();
	
	/**
	 * @Description 返回清算步骤明细日志
	 * @param step   清算步骤编号
	 * @param lines  倒数多少行
	 * @return
	 */
	public String getStepLog(int step,int lines);

}
