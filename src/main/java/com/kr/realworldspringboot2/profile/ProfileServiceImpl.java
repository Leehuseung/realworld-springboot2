package com.kr.realworldspringboot2.profile;

import com.kr.realworldspringboot2.member.Member;
import com.kr.realworldspringboot2.member.MemberRepository;
import com.kr.realworldspringboot2.security.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final ProfileQueryRepository profileQueryRepository;
    private final MemberRepository memberRepository;

    @Override
    public ProfileDTO getProfile(String username, AuthMemberDTO authMemberDTO) {
        Member member = memberRepository.findByUsername(username).get();
        ProfileDTO result = profileQueryRepository.getProfile(member.getId(),authMemberDTO);
        return result;
    }
}
