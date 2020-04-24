package com.hxzy.mapper;

import com.hxzy.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

public interface AdminMapper {

    int insert(Admin record);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    /**
     * 登录
     * @param loginName
     * @return
     */
    Admin login(String loginName);
}