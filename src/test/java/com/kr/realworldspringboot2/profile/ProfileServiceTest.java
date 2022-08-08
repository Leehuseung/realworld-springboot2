package com.kr.realworldspringboot2.profile;

import com.kr.realworldspringboot2.member.Member;
import com.kr.realworldspringboot2.member.MemberRepository;
import com.kr.realworldspringboot2.security.AuthMemberDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    ProfileQueryRepository profileQueryRepository;

    @InjectMocks
    ProfileServiceImpl profileService;

    @Test
    @DisplayName("프로필 조회 테스트")
    public void get_profile() {
        //given
        AuthMemberDTO authMemberDTO = new AuthMemberDTO(2l,"test02@realworld.com","1111",new ArrayList<>());

        ProfileDTO mockProfileDTO = ProfileDTO.builder()
                .username("test01")
                .bio("bio1")
                .image("image1")
                .following(false)
                .build();

        Member mockMember = Member.builder()
                        .id(1l)
                        .password("1111")
                        .username("test01")
                        .email("test01@realworld.com")
                        .build();

        when(memberRepository.findByUsername(any())).thenReturn(Optional.of(mockMember));
        when(profileQueryRepository.getProfile(anyLong(),any())).thenReturn(mockProfileDTO);

        //when
        ProfileDTO profileDTO = profileService.getProfile("test01@realworld.com", authMemberDTO);

        //then
        assertEquals(profileDTO.getUsername(), "test01");
        assertEquals(profileDTO.getBio(), "bio1");
        assertEquals(profileDTO.getImage(),"image1");
        assertEquals(profileDTO.isFollowing(),false);

    }

}