package com.kr.realworldspringboot2.member;

import com.kr.realworldspringboot2.exception.DuplicateRegisterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public long registerMember(RegisterMemberDTO registerMemberDTO) {
        if(memberRepository.findByEmail(registerMemberDTO.getEmail()).isPresent()){
            throw new DuplicateRegisterException("email");
        }

        if(memberRepository.findByUsername(registerMemberDTO.getUsername()).isPresent()){
            throw new DuplicateRegisterException("username");
        }

        Member member = registerMemberDTOtoEntity(registerMemberDTO);

        return memberRepository.save(member).getId();
    }

    @Override
    public MemberDTO findById(long id) {
        Member member = memberRepository.findById(id).get();
        return memberToDTO(member);
    }

    @Override
    public MemberDTO findByEmail(String email) {
        Member member = memberRepository.findByEmail(email).get();
        return memberToDTO(member);
    }

    @Override
    public long updateMember(UpdateMemberDTO updateMemberDTO) {
        Member member = memberRepository.findById(updateMemberDTO.getId()).get();
        updateMemberDTO.applyTo(member);
        memberRepository.save(member);
        return member.getId();
    }
}
