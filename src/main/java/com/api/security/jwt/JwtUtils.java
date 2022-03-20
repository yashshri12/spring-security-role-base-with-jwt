package com.api.security.jwt;

import com.api.security.service.CustomUserDetails;
import com.api.security.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private String SECRET_KEY="SECURITY";
    private int expTime =86400;

    private Jws<Claims> getClaims(String jwtToken){
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
        }catch (Exception e){
            return null;
        }
    }

    public boolean validateToken(String jwt) {
        if(getClaims(jwt)!=null){
            return true;
        }else{
            return false;
        }
    }

    public String getUsernameFromJwtToken(String jwt) {
        return getClaims(jwt).getBody().getSubject();
    }

    public String generateJwtToken(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
     return Jwts.builder()
             .setSubject(customUserDetails.getUsername())
             .setIssuedAt(new Date())
             .setExpiration(new Date(new Date().getTime() + expTime))
             .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
             .compact();
    }
}
