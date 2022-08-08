package com.kr.realworldspringboot2.profile;

import com.kr.realworldspringboot2.security.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/api/profiles/{username}")
    public ProfileDTO getProfile(@PathVariable String username, @AuthenticationPrincipal AuthMemberDTO authMemberDTO){
        return profileService.getProfile(username,authMemberDTO);
    }


    @PostMapping("/api/profiles/{username}/follow")
    public ProfileDTO followUser(@PathVariable String username, @AuthenticationPrincipal AuthMemberDTO authMemberDTO){
        return profileService.followUser(username,authMemberDTO);
    }

    @DeleteMapping("/api/profiles/{username}/follow")
    public ProfileDTO unfollowUser(@PathVariable String username, @AuthenticationPrincipal AuthMemberDTO authMemberDTO){
        return profileService.unFollowUser(username,authMemberDTO);
    }

}
