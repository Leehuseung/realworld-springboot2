package com.kr.realworldspringboot2.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberDTO {
    private String email;
    private String token;
    private String username;
    private String bio;
    private String image;
}
