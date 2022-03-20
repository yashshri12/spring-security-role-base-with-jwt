package com.api.security.jwt;

import com.api.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public AuthTokenFilter(JwtUtils jwtUtils, CustomUserDetailsService customUserDetailsService) {

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

          String jwt = parseJwt(request);

          if (jwt != null && jwtUtils.validateToken(jwt)) {
              String username = jwtUtils.getUsernameFromJwtToken(jwt);
              UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

              UsernamePasswordAuthenticationToken authenticationToken =
                      new UsernamePasswordAuthenticationToken(
                              userDetails,
                              null,
                              userDetails.getAuthorities()
                      );
              authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(authenticationToken);
          }
          filterChain.doFilter(request,response);



    }

    private String parseJwt(HttpServletRequest request){
        String header =request.getHeader("Authorization");

        if(header!=null && header.startsWith("Bearer ")){
           return header.substring(7,header.length());
        }
        return null;
    }
}
