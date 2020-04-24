package com.hxzy.service;

import com.hxzy.common.vo.PageSearch;
import com.hxzy.common.vo.ResponseMessage;
import com.hxzy.entity.Role;

import java.util.List;

public interface RoleService {
    boolean insert(Role record);

    Role selectByPrimaryKey(Integer roleId);

    boolean updateByPrimaryKeySelective(Role record);

    boolean updateByPrimaryKey(Role record);

    /**
     * 分页查询
     * @param pageSearch
     * @return
     */
    ResponseMessage search(PageSearch pageSearch);

    /**
     * 验证角色名称是否存在（新增，修改）
     * @param role
     * @return
     */
    ResponseMessage existsName(Role role);
}
