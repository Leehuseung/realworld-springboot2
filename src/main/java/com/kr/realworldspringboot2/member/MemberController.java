package com.kr.realworldspringboot2.member;

import com.kr.realworldspringboot2.security.AuthMemberDTO;
import com.kr.realworldspringboot2.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final JWTUtil jwtUtil;
    private final MemberService memberService;

    @PostMapping("/api/users")
    public MemberDTO registrationMember(@RequestBody @Valid RegisterMemberDTO registerMemberDTO){
        Long id = memberService.registerMember(registerMemberDTO);
        MemberDTO memberDTO = memberService.findById(id);

        String token = jwtUtil.generateTokenByEmail(memberDTO.getEmail());
        memberDTO.setToken(token);

        return memberDTO;
    }

    @GetMapping("/api/user")
    public MemberDTO getCurrentMember(@AuthenticationPrincipal AuthMemberDTO authMemberDTO){
        MemberDTO memberDTO = memberService.findById(authMemberDTO.getId());
        String token = jwtUtil.generateTokenByEmail(memberDTO.getEmail());
        memberDTO.setToken(token);
        return memberDTO;
    }

    @PutMapping("/api/user")
    public MemberDTO updateMember(@AuthenticationPrincipal AuthMemberDTO authMemberDTO,
                                   @RequestBody @Valid UpdateMemberDTO updateMemberDTO){
        updateMemberDTO.setId(authMemberDTO.getId());
        MemberDTO memberDTO = memberService.updateMember(updateMemberDTO);
        String token = jwtUtil.generateTokenByEmail(memberDTO.getEmail());
        memberDTO.setToken(token);
        return memberDTO;
    }



}
