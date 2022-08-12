package com.kr.realworldspringboot2.member;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private String username;
    private String bio;
    private String image;
    private boolean following;
}
