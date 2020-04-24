package com.hxzy.mapper;

import com.hxzy.common.vo.PageSearch;
import com.hxzy.entity.Role;
import org.apache.catalina.LifecycleState;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface RoleMapper {

    int insert(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * 分页查询
     * @param pageSearch
     * @return
     */
    List<Role> search(PageSearch  pageSearch);

    int existsName(String name);
}