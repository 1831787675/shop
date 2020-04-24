package com.hxzy.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.hxzy.common.vo.ResponseMessage;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//后台登录判断拦截器
@Log4j2
@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

   //登录判断
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ResponseMessage  rm=null;

        //判断如果请求的方式是options，直接放行
        String type=request.getMethod();
        System.out.println(request.getRequestURI()+",请求方式:"+type);
        if(type.equalsIgnoreCase(RequestMethod.OPTIONS.name())){
            return true;
        }

        //判断header是否有token
        String token=request.getHeader("token");
        if(StringUtils.isBlank(token)){
            rm=ResponseMessage.failed(50008,"请先登录后再访问");
            printJson(response, rm);
            return false;
        }else{
            //有token，但是token是无效的
            try{
                boolean result=this.stringRedisTemplate.hasKey("login:user:"+token);
                if(!result){
                    rm=ResponseMessage.failed(50014,"令牌已过期，请重新登录");
                    printJson(response, rm);
                    return false;
                }
            }catch(Exception e){
                log.error(e);
                rm=ResponseMessage.failed(50008,"拦截器连接redis操作失败");
                printJson(response,rm);
                return false;
            }
        }
        return true;
    }

    private void printJson(HttpServletResponse response, ResponseMessage rm) throws IOException {
        //输出给前端(最原始的servlet操作)
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        //把java转换成 json
        String json= JSONObject.toJSONString(rm);
        out.println(json);
        out.flush();
        out.close();
    }
}
