package com.kr.realworldspringboot2.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
class RegisterMemberDTO {
    @Valid
    private User user;

    public String getEmail() {
        return user.getEmail();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getPassword() {
        return user.getPassword();
    }

    public RegisterMemberDTO(String username, String email, String password) {
        User userDTO = new User();
        userDTO.setUsername(username);
        userDTO.setEmail(email);
        userDTO.setPassword(password);
        this.user = userDTO;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    class User {
        @NotEmpty(message = "username can't be empty")
        private String username;
        @NotEmpty(message = "email can't be empty")
        @Email(message = "confirm your email format")
        private String email;
        @NotEmpty(message = "password can't be empty")
        private String password;
    }


}
