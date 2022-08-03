package com.kr.realworldspringboot2.member;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberServiceImpl memberService;

    @Test
    @DisplayName("사용자 등록 테스트")
    public void member_registration() {
        //given
        Member member = Member.builder()
                .id(1l)
                .username("test06")
                .email("test06@realworld.com")
                .password("1111")
                .build();

        when(memberRepository.save(any())).thenReturn(member);

        RegisterMemberDTO registerMemberDTO = new RegisterMemberDTO("test06","test06@realworld.com","1111");

        //when
        Long id = memberService.registerMember(registerMemberDTO);

        //then
        assertEquals(id,1l);
    }

    
    @Test
    @DisplayName("사용자 조회 테스트")
    public void member_get() {
        //given
        Member member = Member.builder()
                .id(1l)
                .username("test06")
                .email("test06@realworld.com")
                .password("1111")
                .build();

        when(memberRepository.findById(any())).thenReturn(Optional.of(member));

        //when
        MemberDTO memberDTO = memberService.findById(1l);
        
        //then
        assertEquals(memberDTO.getUsername(),"test06");
        assertEquals(memberDTO.getEmail(),"test06@realworld.com");
        assertEquals(memberDTO.getBio(),null);
        assertEquals(memberDTO.getImage(),null);
    }

    @Test
    @DisplayName("사용자 업데이트 테스트")
    public void member_update(){

        UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO(
                "test06","test06@realworld.com","bio","image"
        );

        //given
        Member member = Member.builder()
                .id(1l)
                .username("test06")
                .password("1111")
                .email("test06@realworld.com")
                .build();

        when(memberRepository.findById(any())).thenReturn(Optional.of(member));

        //when
        MemberDTO memberDTO = memberService.updateMember(updateMemberDTO);

        //then
        assertEquals(memberDTO.getUsername(),"test06");
        assertEquals(memberDTO.getEmail(),"test06@realworld.com");
        assertEquals(memberDTO.getToken(),null);
        assertEquals(memberDTO.getBio(),"bio");
        assertEquals(memberDTO.getImage(),"image");
    }
}