package com.kr.realworldspringboot2.member;

import com.kr.realworldspringboot2.exception.DuplicateRegisterException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public long registerMember(RegisterMemberDTO registerMemberDTO) {
        if(memberRepository.findByEmail(registerMemberDTO.getEmail()).isPresent()){
            throw new DuplicateRegisterException("email");
        }

        if(memberRepository.findByUsername(registerMemberDTO.getUsername()).isPresent()){
            throw new DuplicateRegisterException("username");
        }

        Member member = registerMemberDTOtoEntity(registerMemberDTO);

        member.setPassword(passwordEncoder.encode(member.getPassword()));

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
    public MemberDTO updateMember(UpdateMemberDTO updateMemberDTO) {
        if(updateMemberDTO.getPassword() != null){
            updateMemberDTO.setPassword(passwordEncoder.encode(updateMemberDTO.getPassword()));
        }
        Member member = memberRepository.findById(updateMemberDTO.getId()).get();
        updateMemberDTO.applyTo(member);
        memberRepository.save(member);
        return memberToDTO(member);
    }
}
