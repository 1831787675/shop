package com.hxzy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hxzy.common.vo.BSTable;
import com.hxzy.common.vo.PageSearch;
import com.hxzy.common.vo.ResponseMessage;
import com.hxzy.entity.Role;
import com.hxzy.mapper.RoleMapper;
import com.hxzy.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean insert(Role record) {
        return this.roleMapper.insert(record)>0;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Role selectByPrimaryKey(Integer roleId) {
        return this.roleMapper.selectByPrimaryKey(roleId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateByPrimaryKeySelective(Role record) {
        return this.roleMapper.updateByPrimaryKeySelective(record)>0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateByPrimaryKey(Role record) {
        return this.roleMapper.updateByPrimaryKey(record)>0;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ResponseMessage search(PageSearch pageSearch) {
        //分页
        PageHelper.startPage(pageSearch.getPage(),pageSearch.getSize());
        List<Role> arr=this.roleMapper.search(pageSearch);
        Page  pg=(Page) arr;

        //组成类
        BSTable bs=new BSTable();
        bs.setTotal(pg.getTotal());
        bs.setRows(arr);

        return ResponseMessage.success("ok",bs);
    }

    /**
     * 验证角色名称是否存在（新增，修改）
     * @param role
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ResponseMessage existsName(Role role) {
        //可以使用
        boolean result=true;
        //新增
        if(role.getRoleId()==null || role.getRoleId()==0){
            int count=this.roleMapper.existsName(role.getRoleName());
            result =(count==0);
        }else{
            //修改
            //先查询数据库
            Role dbRole =this.roleMapper.selectByPrimaryKey(role.getRoleId());
            //修改的值与数据库不一致
            if(!dbRole.getRoleName().equals(role.getRoleName())){
                int count=this.roleMapper.existsName(role.getRoleName());
                result =(count==0);
            }
        }

        return result? ResponseMessage.success("ok",true): ResponseMessage.failed(500,"该值已经被使用");
    }
}
