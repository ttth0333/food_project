package com.tringuyen.food_project.jwt;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    private Gson gson = new Gson();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //cắt header và lấy token
        String token = getTokenFromHeader(request);
        if (token != null) {
            //Kiểm tra token có phải do hệ thống của mình sinh ra hay không
            if(jwtTokenHelper.validateToken(token)) {
                //token hợp lệ --- tham số thứ 3 là role
                //{"type":"refresh","username":"nguyenvana@gmail.com"}
                String json = jwtTokenHelper.decodeToken(token);
                Map<String, Object> map = gson.fromJson(json, Map.class); // đối tượng or map
                System.out.println("Check: " + json + " _ " + map.get("type").toString());
                if (StringUtils.hasText(map.get("type").toString()) &&
                        !map.get("type").toString().equals("refresh")) { // kiểm tra xem map có rỗng không và có khác refresh
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("","", new ArrayList<>());
                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    securityContext.setAuthentication(authenticationToken);
                }

            }
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        // Lấy giá trị token ở header có key là Authorization
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            // substring: dùng để cắt chuỗi
            String finalToken = finalToken = bearerToken.substring(7);
            return finalToken;
        } else {
            return null;
        }
    }
}
