package com.sge.clear.admin.service;

import java.util.List;

import com.sge.clear.admin.model.ClearStepDO;

/**
 * @Description 清算单步执行服务类
 * @author yh 2014-05-14
 *
 */
public interface StepDebugService {
	
    /** 
     * @Description 加载清算步骤配置
     */
	public List<ClearStepDO> loadStep();
	
    /** 
     * @Description 返回清算步骤的状态(主要是status字段，标识目前清算的执行情况)
     */
	public List<ClearStepDO> getStepStatus();
	
	/**
	 * @Description 异步执行指定的清算步骤(单步)
	 * @param step 步骤序列号
	 * @return 返回清算步骤的状态(主要是status字段，标识目前清算的执行情况)
	 */
	public List<ClearStepDO> runStep(String clearDate,int step);
	
    /** 
     * @Description 返回清算日志明细
     */
	public String getTaskLog();
	
	/**
	 * @Description 返回清算步骤明细日志
	 * @param step   清算步骤编号
	 * @param lines  倒数多少行
	 * @return
	 */
	public String getStepLog(String clearDate, int step, int lines);

}
