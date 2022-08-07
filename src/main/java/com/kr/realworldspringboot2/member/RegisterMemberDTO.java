package com.kr.realworldspringboot2.member;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("user")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
class RegisterMemberDTO {
        @NotEmpty(message = "username can't be empty")
        private String username;
        @NotEmpty(message = "email can't be empty")
        @Email(message = "confirm your email format")
        private String email;
        @NotEmpty(message = "password can't be empty")
        private String password;
}
