package com.kr.realworldspringboot2.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@Getter
@Setter
public class UpdateMemberDTO {
    @Valid
    private User user;

    private long id;

    public String getEmail() {
        return user.getEmail();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getBio() {
        return user.getBio();
    }

    public String getImage() {
        return user.getImage();
    }

    public UpdateMemberDTO(String username, String email, String bio, String image) {
        UpdateMemberDTO.User userDTO = new UpdateMemberDTO.User();
        userDTO.setUsername(username);
        userDTO.setEmail(email);
        userDTO.setBio(bio);
        userDTO.setImage(image);
        this.user = userDTO;
    }

    void applyTo(Member m) {
        if(this.user.getUsername() != null) m.setUsername(this.user.getUsername());
        if(this.user.getEmail() != null) m.setEmail(this.user.getEmail());
        if(this.user.getPassword() != null) m.setPassword(this.user.getPassword());
        if(this.user.getBio() != null) m.setBio(this.user.getBio());
        if(this.user.getImage() != null) m.setImage(this.user.getImage());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    class User {
        private String username;
        @Email(message = "confirm your email format")
        private String email;
        private String password;
        private String bio;
        private String image;
    }
}
