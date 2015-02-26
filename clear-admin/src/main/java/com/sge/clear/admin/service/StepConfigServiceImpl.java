package com.sge.clear.admin.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sge.clear.admin.dao.ClearStepDOMapper;
import com.sge.clear.admin.model.ClearStepDO;

public class StepConfigServiceImpl implements StepConfigService{
	
	private static final Logger logger = LoggerFactory.getLogger("file.log");
	
	@Resource(name = "clearStepDOMapper")
	private ClearStepDOMapper clearStepDOMapper;

	private List<ClearStepDO> stepDOList;
	
	@Override
	public List<ClearStepDO> loadStep() {
		logger.info("StepConfigService.loadStep()");
		stepDOList = clearStepDOMapper.getAll();
		return stepDOList;
	}
	
	@Override
	public List<ClearStepDO> deleteByStep(int step) {
		logger.info("StepConfigService.deleteStepById({})",step);
		clearStepDOMapper.deleteByPrimaryKey(step);
		stepDOList = clearStepDOMapper.getAll();
		return stepDOList;
	}

	@Override
	public int insertStep(ClearStepDO record) {	
		//获取当前时间作为记录插入时间和更新时间
		Date now = new Date(); 
		record.setGmtCreated(now);
		record.setGmtModified(now);
		logger.info("StepConfigService.insertStep,stepDO: " + record.toString());
		return clearStepDOMapper.insertSelective(record);
	}

	@Override
	public int updateStepByStep(ClearStepDO record) {
		logger.info("StepConfigService.updateStepById,stepDO: " + record.toString());
		Date now = new Date(); 
		record.setGmtModified(now);
		return clearStepDOMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public ClearStepDO getStepById(int step) {
		logger.info("StepConfigService.getStepById,step={}",step);
		return clearStepDOMapper.selectByPrimaryKey(step);		
	}

}
