package com.hxzy.service;

import com.hxzy.common.vo.ResponseMessage;
import com.hxzy.entity.Admin;

public interface AdminService {

    boolean insert(Admin record);

    ResponseMessage selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(Admin record);

    boolean updateByPrimaryKey(Admin record);

    /**
     * 登录
     * @param admin
     * @return
     */
    ResponseMessage login(Admin admin);

    /**
     * 根据 header中的令牌去redis中取用户信息
     * @param token
     * @return
     */
    ResponseMessage getAdminInfo(String token);
}
