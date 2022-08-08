package com.kr.realworldspringboot2.profile;

import com.kr.realworldspringboot2.security.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/api/profiles/{username}")
    public ProfileDTO getProfile(@PathVariable String username, @AuthenticationPrincipal AuthMemberDTO authMemberDTO){
        ProfileDTO profileDTO = profileService.getProfile(username,authMemberDTO);
        return profileDTO;
    }



}
