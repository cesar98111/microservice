package com.productos.product.security.jwt;

import com.productos.product.security.jwt.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtTokenFromRequest(request);
        logger.info(jwtProvider.validateToken(token));
        try{
            if(StringUtils.hasText(token) && jwtProvider.validateToken(token)){

                String userId = jwtProvider.getUserIdFromJwtToken(token);

                User result = new User(1,"paco","12314");

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    result,
                                    result.getPassword(),
                                    null
                            );
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);



            }


        }catch (Exception ex){
            logger.info("no pasa");
        }
        filterChain.doFilter(request,response);
    }

    private String getJwtTokenFromRequest(HttpServletRequest http){
        String bearerToken = http.getHeader(JwtProvider.TOKEN_HEADER);

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProvider.TOKEN_PREFIC)){
            return bearerToken.substring(JwtProvider.TOKEN_PREFIC.length());
        }
        return null;
    }
}
