package com.gate.gateway.security.jwt;

import ch.qos.logback.core.util.StringUtil;
import com.gate.gateway.user.entity.User;
import com.gate.gateway.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtTokenFromRequest(request);

        try{

            if(StringUtils.hasText(token) && jwtProvider.validateToken(token)){

                String userId = jwtProvider.getUserIdFromJwtToken(token);
                logger.info("pasa por aqui");
                Optional<User> result = userService.findById(userId);
                if(result.isPresent()){
                    User user = result.get();
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    user.getAuthorities()
                            );
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                }

            }


        }catch (Exception ex){
            logger.info("no pasa" + ex);
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
