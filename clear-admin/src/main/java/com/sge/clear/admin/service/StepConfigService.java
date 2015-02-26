package com.sge.clear.admin.service;

import java.util.List;

import com.sge.clear.admin.model.ClearStepDO;

/**
 * @Description 清算步骤增删改Service
 * @author yh 2014-05-14
 */
public interface StepConfigService {

    /** 
     * @Description 加载清算步骤配置
     */
	public List<ClearStepDO> loadStep();
	
    /** 
     * @Description 加载清算步骤配置
     */
	public ClearStepDO getStepById(int id);
	
    /** 
     * @Description 根据ID删除清算步骤
     */
	public List<ClearStepDO> deleteByStep(int id);
	
    /** 
     * @Description 新增清算步骤
     */
	public int insertStep(ClearStepDO record);
	
    /** 
     * @Description 根据ID更新清算步骤
     */
	public int updateStepByStep(ClearStepDO record);
	
}
