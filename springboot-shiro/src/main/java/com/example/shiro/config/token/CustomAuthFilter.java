package com.example.shiro.config.token;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.shiro.utils.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//AuthenticatingFilter
public class CustomAuthFilter extends AuthenticatingFilter {

    /**
     * shiro token认证流程
     * 1. 调用isAccessAllowed()方法，是否放行请求
     * 2. 如果步骤isAccessAllowed方法拒绝访问请求，则执行onAccessDenied()方法，再确认前端传递的token有值的情况下，调用executeLogin()方法
     * 3. executeLogin()方法内部调用createToken()，将前端的token封装成自定义的token实现（实现AuthenticationToken接口），默认的createToken()生成方式采用username，password生成
     * 4. executeLogin()核心逻辑走subject.login(token)调用，失败则进入onLoginFailure()处理登录失败逻辑
     * 5. subject.login(token)底层调用doAuthenticate()方法，这里会区分配置的realms是多个还是单个，走不同的逻辑，案例配置的单个doSingleRealmAuthentication()
     * 6. doSingleRealmAuthentication()方法中执行realm.supports(token)校验，所以自定义的Realm需要重写supports，确保realm使用的是自定义的token类
     * 7. 调用getAuthenticationInfo()方法，如果有缓存则拿缓存逻辑（需要Realm配置缓存处理器），无则调用自定义的Realm的doGetAuthenticationInfo()方法
     * 8. doGetAuthenticationInfo()获取到认证信息后走assertCredentialsMatch()加密逻辑，需要Realm配置加密
     *
     * 9. 如果RequiresRoles,RequiresPermissions注解，则调用Realm的doGetAuthorizationInfo()方法
     */

    /**
     * 生成token
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        //获取请求token
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            // 增加跨域支持
            String myOrigin = httpServletRequest.getHeader("origin");
            httpResponse.setContentType("application/json;charset=utf-8");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with, Access-Token, datasource-Key");
            httpResponse.setHeader("Access-Control-Allow-Origin", myOrigin);
            httpResponse.setCharacterEncoding("UTF-8");
            // 返回错误状态信息
            Map<String, String> map = new HashMap<>();
            map.put("code", "1001");
            map.put("msg", "token失效，请重新登录");
            httpResponse.getWriter().write(JSONObject.toJSONString(map));
            return null;
        }
        return new AuthToken(token);
    }
    /**
     * 拒绝访问的请求回调
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isEmpty(token)){
            httpServletResponse.setHeader("Access-Control-Allow-Credentials","true");
            httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
            httpServletResponse.setCharacterEncoding("UTF-8");
            Map<String, String> map = new HashMap<>();
            map.put("code", "1001");
            map.put("msg", "token失效，请重新登录");
            httpServletResponse.getWriter().write(JSONObject.toJSONString(map));
            return false;
        } else {
            //token jwt校验失败
            if (!JWTUtil.verify(token)){
                httpServletResponse.setHeader("Access-Control-Allow-Credentials","true");
                httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
                httpServletResponse.setCharacterEncoding("UTF-8");
                Map<String, String> map = new HashMap<>();
                map.put("code", "1001");
                map.put("msg", "token校验失败");
                httpServletResponse.getWriter().write(JSONObject.toJSONString(map));
                return false;
            }
        }
        return executeLogin(servletRequest, servletResponse);
    }

    /**
     * 是否允许访问
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest)request).getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        return false;
    }


    /**
     * token登录失败调用
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials","true");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setCharacterEncoding("UTF-8");
        try {
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            Map<String, String> map = new HashMap<>();
            map.put("code", "1001");
            map.put("msg", "登录失败，请重新登录");
            httpServletResponse.getWriter().write(JSONObject.toJSONString(map));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return false;
    }
}
