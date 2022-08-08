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
    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;

    @Override
    public ProfileDTO getProfile(String username, AuthMemberDTO authMemberDTO) {
        Member member = memberRepository.findByUsername(username).get();
        ProfileDTO result = profileQueryRepository.getProfile(member.getId(),authMemberDTO);
        return result;
    }

    @Override
    public ProfileDTO followUser(String username, AuthMemberDTO authMemberDTO) {
        Member followMember = memberRepository.findByUsername(username).get();
        Member member = memberRepository.findById(authMemberDTO.getId()).get();
        Follow follow = Follow.builder()
                .member(member)
                .followMember(followMember)
                .build();
        profileRepository.save(follow);
        return profileQueryRepository.getProfile(followMember.getId(),authMemberDTO);
    }

    @Override
    public ProfileDTO unFollowUser(String username, AuthMemberDTO authMemberDTO) {
        Member followMember = memberRepository.findByUsername(username).get();
        Follow follow = profileRepository.findByMemberIdAndFollowUsername(authMemberDTO.getId(), followMember.getId()).get();
        profileRepository.delete(follow);
        return profileQueryRepository.getProfile(followMember.getId(),authMemberDTO);
    }
}
