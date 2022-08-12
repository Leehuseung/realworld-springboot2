package com.kr.realworldspringboot2.util;

import com.kr.realworldspringboot2.security.AuthMemberDTO;
import com.kr.realworldspringboot2.security.AuthUserDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

    @Value("${jwt.start}")
    String TOKEN_START;

    @Value("${jwt.secret}")
    private String SECURITY_KEY;

    @Value("${jwt.token-validity-in-seconds}")
    private long TOKEN_EXPIRE_SECONDS;

    private final String DEFAULT_RULE = "ROLE_USER";

    @Autowired
    AuthUserDetailsService authUserDetailsService;

    public String generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        AuthMemberDTO memberDTO = (AuthMemberDTO)authentication.getPrincipal();
        try{
            return Jwts.builder()
                    .setSubject(memberDTO.getEmail())
                    .setIssuedAt(new Date())
                    .setExpiration(Date.from(ZonedDateTime.now()
                            .plusMinutes(TOKEN_EXPIRE_SECONDS).toInstant()))
                    .claim("roles", DEFAULT_RULE)
                    .signWith(SignatureAlgorithm.HS256, SECURITY_KEY.getBytes("UTF-8"))
                    .compact();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String generateTokenByEmail(String email) {
        try{
            return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(Date.from(ZonedDateTime.now()
                            .plusMinutes(TOKEN_EXPIRE_SECONDS).toInstant()))
                    .claim("roles", DEFAULT_RULE)
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

            UserDetails authMemberDTO = authUserDetailsService.loadUserByUsername(claims.getSubject());

            return new UsernamePasswordAuthenticationToken(authMemberDTO, tokenStr, authorities);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getToken(String authorization) {
        return authorization.substring(TOKEN_START.length()+1);
    }


}