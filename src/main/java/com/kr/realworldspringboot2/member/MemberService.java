package com.kr.realworldspringboot2.member;

interface MemberService {

    long registerMember(RegisterMemberDTO registerMemberDTO);

    MemberDTO findById(long id);

    MemberDTO findByEmail(String email);

    long updateMember(UpdateMemberDTO updateMemberDTO);


    default Member registerMemberDTOtoEntity(RegisterMemberDTO registerMemberDTO){
        return Member.builder()
                .username(registerMemberDTO.getUsername())
                .email(registerMemberDTO.getEmail())
                .password(registerMemberDTO.getPassword())
                .build();
    }

    default MemberDTO memberToDTO(Member member){
        return MemberDTO.builder()
                .username(member.getUsername())
                .email(member.getEmail())
                .bio(member.getBio())
                .image(member.getImage())
                .build();
    }


}
