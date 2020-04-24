package com.hxzy.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * rw_admin
 * @author 
 */
@Data
public class Admin implements Serializable {
    private Integer id;

    private String loginName;

    @JSONField(serialize = false)
    private String loginPwd;

    /**
     * 盐
     */
    @JSONField(serialize = false)
    private String salt;

    private Integer roleId;

    //角色名称
    private String roleName;

    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date lastLoginTime;

    private String lastLoginIP;

    /**
     * 0禁用，1启用
     */
    private Integer state;

    private static final long serialVersionUID = 1L;
}