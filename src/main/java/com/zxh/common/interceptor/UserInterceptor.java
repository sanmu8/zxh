package com.zxh.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxh.common.context.UserContext;
import com.zxh.entity.AjaxResult;
import com.zxh.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token;
        try {
            token = request.getHeader("authorization").substring("Bearer ".length());
        } catch (Exception e) {
            token = "";
        }
        if (JwtUtil.checkToken(token)) {
            Claims claims = JwtUtil.parseJwt(token);
            UserContext.setCurrentUser((Integer) claims.get("id"));
            return true;
        } else {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(AjaxResult.tokenError()));
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clear();
    }
}
