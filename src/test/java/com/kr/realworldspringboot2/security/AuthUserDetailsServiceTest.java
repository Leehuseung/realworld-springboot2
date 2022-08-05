package com.kr.realworldspringboot2.security;

import com.kr.realworldspringboot2.member.Member;
import com.kr.realworldspringboot2.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthUserDetailsServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    AuthUserDetailsService authUserDetailsService;


    @Test
    void loadUserByUsername() {
        //given
        String username = "test06@realworld.com";
        String password = "1111";

        Member test06 = Member.builder()
                .id(1l)
                .username(username)
                .password(password)
                .email("test06")
                .build();

        when(memberRepository.findByEmail(username)).thenReturn(Optional.of(test06));

        //when
        UserDetails user = authUserDetailsService.loadUserByUsername(username);

        //then
        assertEquals(user.getUsername(), username);
        assertEquals(user.getPassword(), password);
    }

}