package com.sge.clear.admin.dao;

import com.sge.clear.admin.model.UserDO;

public interface UserDOMapper {
    int deleteByPrimaryKey(String username);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);
}