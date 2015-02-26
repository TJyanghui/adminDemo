package com.sge.clear.admin.dao;

import java.util.List;

import com.sge.clear.admin.model.ClearStepDO;

public interface ClearStepDOMapper {
    int deleteByPrimaryKey(Integer step);

    int insert(ClearStepDO record);

    int insertSelective(ClearStepDO record);

    ClearStepDO selectByPrimaryKey(Integer step);

    int updateByPrimaryKeySelective(ClearStepDO record);

    int updateByPrimaryKey(ClearStepDO record);
	
	List<ClearStepDO> getAll();
	
	List<ClearStepDO> getEnable();
}