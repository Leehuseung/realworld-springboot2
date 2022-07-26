package com.kr.realworldspringboot2.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

    private String SECURITY_KEY = "realworldSecret";

    private long expire = 60 * 24 * 30;

    public String generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        try{
            return Jwts.builder()
                    .setSubject(authentication.getName())
                    .setIssuedAt(new Date())
                    .setExpiration(Date.from(ZonedDateTime.now()
                            .plusMinutes(expire).toInstant()))
                    .claim("roles", authorities)
                    .signWith(SignatureAlgorithm.HS256, SECURITY_KEY.getBytes("UTF-8"))
                    .compact();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Authentication getAuthentication(String tokenStr) {
        try {

            DefaultJws defaultJws = (DefaultJws) Jwts
                    .parser()
                    .setSigningKey(SECURITY_KEY.getBytes("UTF-8"))
                    .parseClaimsJws(tokenStr);

            DefaultClaims claims = (DefaultClaims) defaultJws.getBody();

            String roles = (String) claims.get("roles");

            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(roles.split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            User principal = new User(claims.getSubject(), "", authorities);

            return new UsernamePasswordAuthenticationToken(principal, tokenStr, authorities);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public String getEmailbyHeader(String header) {
//        if(header == null){
//            return null;
//        }
//        String token = getToken(header);
//        return validateAndExtract(token);
//    }

    public String getToken(String authorization) {
        return authorization.substring(6);
    }
}

