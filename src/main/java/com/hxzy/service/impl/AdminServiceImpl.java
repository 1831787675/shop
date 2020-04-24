package com.hxzy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hxzy.common.util.MD5Util;
import com.hxzy.common.vo.ResponseMessage;
import com.hxzy.entity.Admin;
import com.hxzy.mapper.AdminMapper;
import com.hxzy.service.AdminService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Log4j2
@Transactional
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean insert(Admin record) {
         record.setSalt(MD5Util.randomSalt(5));
         String pwd=MD5Util.MD5Encode(record.getLoginPwd(),record.getSalt());
        return this.adminMapper.insert(record)>0;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ResponseMessage selectByPrimaryKey(Integer id) {
        Admin  admin=this.adminMapper.selectByPrimaryKey(id);
        return  ResponseMessage.success("ok",admin);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateByPrimaryKeySelective(Admin record) {
        return this.adminMapper.updateByPrimaryKeySelective(record)>0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateByPrimaryKey(Admin record) {
        return this.adminMapper.updateByPrimaryKey(record)>0;
    }

    //对数据库写操作
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ResponseMessage login(Admin admin) {
        ResponseMessage  rm=null;
        //查询
        Admin dbAdmin=this.adminMapper.login(admin.getLoginName());

        try{
            if(dbAdmin==null){
                rm=ResponseMessage.failed(404,"用户名或密码错误");
            }else{
                String md5Pwd=MD5Util.MD5Encode(admin.getLoginPwd(),  dbAdmin.getSalt());
                if(md5Pwd.equals(dbAdmin.getLoginPwd())){
                    //判断是否锁定
                    if(dbAdmin.getState()==0){
                        rm=ResponseMessage.failed(406,"该账户被锁定");
                    }else{
                        //更新数据库(最后登录时间)
                        admin.setId(dbAdmin.getId());
                        admin.setLoginName(null);
                        admin.setLoginPwd(null);
                        admin.setLastLoginTime(new Date());
                        this.adminMapper.updateByPrimaryKeySelective(admin);

                        //成功的,返回 自定义 字符串(令牌)
                        String token= UUID.randomUUID().toString();
                        //登录成功以后用户信息应该存放到 redis中 , 前端后分离项目，sessionId无效的
                        String adminJson= JSONObject.toJSONString(dbAdmin);
                        //存储到redis中
                        stringRedisTemplate.opsForValue().set("login:user:"+token,  adminJson,30L, TimeUnit.MINUTES);

                        rm=ResponseMessage.success("ok", token);
                    }

                }else{
                    rm=ResponseMessage.failed(405,"用户名或密码错误");
                }
            }

        }catch (Exception e) {
            //记录日志
            log.error(e);
            e.printStackTrace();
        }
        return rm;
    }

    /**
     * 根据 header中的令牌去redis中取用户信息
     * @param token
     * @return
     */
    @Override
    public ResponseMessage getAdminInfo(String token) {
        ResponseMessage rm=null;

        try{
            String info=this.stringRedisTemplate.opsForValue().get("login:user:"+token);
            //要么token错误，要么是已经过期了
            if(StringUtils.isBlank(info)){
                 rm=ResponseMessage.failed(500,"无效令牌或登录已过期!");
            }else{
                //字符串转 java对象
                Admin  admin= JSONObject.parseObject(info,Admin.class);
                rm=ResponseMessage.success("ok",admin);
            }
        }catch(Exception e){
            log.error(e);
            e.printStackTrace();
        }
        return rm;
    }
}
