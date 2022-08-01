package com.kr.realworldspringboot2.member;

import com.kr.realworldspringboot2.exception.DuplicateRegisterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Long registerMember(RegisterMemberDTO registerMemberDTO) {
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
}
