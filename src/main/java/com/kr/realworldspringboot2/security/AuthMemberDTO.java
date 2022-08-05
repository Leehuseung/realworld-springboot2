package com.kr.realworldspringboot2.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class AuthMemberDTO extends User {

    private long id;
    private String email;
    private String username;
    private String bio;
    private String image;
    private String token;


    public AuthMemberDTO(long id,String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(email,password,authorities);
        this.email = email;
        this.id = id;
    }
}