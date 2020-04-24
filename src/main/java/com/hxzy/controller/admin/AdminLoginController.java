package com.hxzy.controller.admin;

import com.hxzy.common.vo.ResponseMessage;
import com.hxzy.entity.Admin;
import com.hxzy.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "/admin")
@RestController
public class AdminLoginController {

    @Autowired
    private AdminService adminService;

    /**
     * 登录操作
     * @param admin
     * @param request
     * @return
     */
    @PostMapping(value = "/login")
    public ResponseMessage login( Admin admin , HttpServletRequest request){
        //得到远程访问的ip
        String ip=request.getRemoteAddr();
        admin.setLastLoginIP(ip);

       return this.adminService.login(admin);
    }


    /**
     * 根据 header中的令牌去redis中取用户信息
     * @param request
     * @return
     */
    @GetMapping(value = "/info")
    public ResponseMessage getAdminInfo(HttpServletRequest request){
        ResponseMessage  rm=null;

        String token=request.getHeader("token");

        if(StringUtils.isBlank(token)){
            rm=ResponseMessage.failed(400,"令牌无效");
        } else{
            rm=this.adminService.getAdminInfo(token);
        }
        return rm;
    }


}
