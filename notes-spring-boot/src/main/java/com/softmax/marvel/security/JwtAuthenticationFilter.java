package com.softmax.marvel.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import io.jsonwebtoken.*;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


/**
 * token的校验
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 *
 * @author : Jarvis
 * @date : 2018/5/12
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith(JwtTokenUtil.BEARER)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    /**
     * 获取token 以及校验token的合法性
     *
     * @param request HttpServletRequest
     * @return String
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 从Header中拿到token
        String token = request.getHeader("Authorization");
        if (token != null) {
            String user;
            Claims claims;
            try {
                // 解析 Token
                claims = Jwts.parser()
                        .setSigningKey(JwtTokenUtil.SECRET)
                        .parseClaimsJws(token.replace("Bearer ", ""))
                        .getBody();
                // 取用户名
                user = claims.getSubject();
            } catch (ExpiredJwtException e) {
                throw new BadCredentialsException("Token已过期");
            } catch (UnsupportedJwtException e) {
                throw new UnsupportedJwtException("Token格式错误");
            } catch (MalformedJwtException e) {
                throw new MalformedJwtException("Token没有被正确构造");
            } catch (SignatureException e) {
                throw new SignatureException("签名失败");
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("非法参数异常");
            }
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }

}
