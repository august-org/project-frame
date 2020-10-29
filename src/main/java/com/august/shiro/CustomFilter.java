package com.august.shiro;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.august.constant.Constant;
import com.august.entity.Result;
import com.august.exception.CustomException;
import com.august.exception.code.RestCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/21
 */
@Slf4j
public class CustomFilter extends AccessControlFilter {
    /**
     * 如果返回true，流转到下一个链式调用
     * 返回false，流转到onAccessDenied方法
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    /**
     * 如果返回值，流转到下一个链式调用(拦截器/过滤器…)
     * 返回false，不会流转到下一个链式调用
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        log.info("request 接口请求方式:{}", httpServletRequest.getMethod());
        log.info("request 接口地址:{}", httpServletRequest.getRequestURI());
        String accessToken = httpServletRequest.getHeader(Constant.ACCESS_TOKEN);
        try {
            if (StringUtils.isEmpty(accessToken)) {
                throw new CustomException(RestCode.TOKEN_NOT_NULL);
            }
            CustomToken passwordToken = new CustomToken(accessToken);
            getSubject(request, response).login(passwordToken);
        } catch (CustomException e) {
            customResponse(response, e.getCode(), e.getMsg());
            return false;
        } catch (AuthenticationException e) {
            if (e.getCause() instanceof CustomException) {
                CustomException exception = (CustomException) e.getCause();
                customResponse(response, exception.getCode(), exception.getMsg());
            } else {
                customResponse(response, RestCode.AUTHORIZATION_TOKEN_ERROR.getCode(), RestCode.AUTHORIZATION_TOKEN_ERROR.getMsg());
            }
            return false;
        } catch (Exception e) {
            customResponse(response, RestCode.SYSTEM_ERROR.getCode(), RestCode.SUCCESS.getMsg());
            return false;
        }

        return true;
    }

    /**
     * 自定义响应前端
     *
     * @param response
     * @param code
     * @param msg
     */
    private void customResponse(ServletResponse response, int code, String msg) {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setCharacterEncoding("UTF-8");
        String string = JSON.toJSONString(Result.get(code, msg));
        try {
            OutputStream stream = response.getOutputStream();
            stream.write(string.getBytes("UTF-8"));
            stream.flush();
            try {
                stream.close();
            } catch (IOException e) {
                log.error("OutputStream error:{}", e);
            }
        } catch (IOException e) {
            log.error("customResponse error:{}", e);
        }
    }
}
