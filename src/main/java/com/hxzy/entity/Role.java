package com.hxzy.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * rw_role
 * @author 
 */
@Data
public class Role implements Serializable {
    private Integer roleId;

    private String roleName;

    private static final long serialVersionUID = 1L;
}