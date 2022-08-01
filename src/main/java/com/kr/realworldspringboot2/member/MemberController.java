package com.kr.realworldspringboot2.member;

import com.kr.realworldspringboot2.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final JWTUtil jwtUtil;
    private final MemberService memberService;

    @PostMapping("/api/users")
    public JSONObject registrationMember(@RequestBody @Valid RegisterMemberDTO registerMemberDTO){
        Long id = memberService.registerMember(registerMemberDTO);
        MemberDTO memberDTO = memberService.findById(id);

        String token = jwtUtil.generateTokenByEmail(memberDTO.getEmail());
        memberDTO.setToken(token);

        return dtoToJson(memberDTO);
    }

    public JSONObject dtoToJson(MemberDTO memberDTO){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user",memberDTO);
        return jsonObject;
    }


}
