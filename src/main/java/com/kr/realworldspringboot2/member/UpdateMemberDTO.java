package com.kr.realworldspringboot2.member;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("user")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class UpdateMemberDTO {
    private long id;
    private String username;
    @Email(message = "confirm your email format")
    private String email;
    private String password;
    private String bio;
    private String image;

    void applyTo(Member m) {
        if(this.getUsername() != null) m.setUsername(this.getUsername());
        if(this.getEmail() != null) m.setEmail(this.getEmail());
        if(this.getPassword() != null) m.setPassword(this.getPassword());
        if(this.getBio() != null) m.setBio(this.getBio());
        if(this.getImage() != null) m.setImage(this.getImage());
    }

}
