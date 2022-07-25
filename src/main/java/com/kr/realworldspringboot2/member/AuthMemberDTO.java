package com.kr.realworldspringboot2.member;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class AuthMemberDTO extends User {

    private String email;
    private String username;
    private String bio;
    private String image;


    public AuthMemberDTO(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(email,password,authorities);
        this.email = email;
    }
}