package com.example.blogging.website.config;

import com.example.blogging.website.security.CustomeUserDetailsService;
import com.example.blogging.website.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            email = jwtService.extractUserName(token);
        }
        //Its time to validate the token
        if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = context.getBean(CustomeUserDetailsService.class).loadUserByUsername(email);
            if(jwtService.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
           authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
           SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        //Connecting to next filter
        filterChain.doFilter(request,response);
    }
}
