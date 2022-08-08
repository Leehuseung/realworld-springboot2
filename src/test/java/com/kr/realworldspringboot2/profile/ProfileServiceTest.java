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

    @Mock
    ProfileRepository profileRepository;

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

    @Test
    @DisplayName("Follow 테스트")
    public void follow_test() {
        //given
        //test02가 test01를 Follow한다.
        AuthMemberDTO authMemberDTO = new AuthMemberDTO(2l,"test02@realworld.com","1111",new ArrayList<>());

        ProfileDTO mockProfileDTO = ProfileDTO.builder()
                .username("test01")
                .bio("bio1")
                .image("image1")
                .following(true)
                .build();

        Member loginMember = Member.builder()
                .id(2l)
                .password("1111")
                .username("test02")
                .email("test02@realworld.com")
                .build();

        Member mockMember = Member.builder()
                .id(1l)
                .password("1111")
                .username("test01")
                .email("test01@realworld.com")
                .build();

        //when
        when(memberRepository.findByUsername("test01")).thenReturn(Optional.of(mockMember));
        when(memberRepository.findById(2l)).thenReturn(Optional.of(loginMember));
        when(profileQueryRepository.getProfile(anyLong(),any())).thenReturn(mockProfileDTO);

        //then
        ProfileDTO profileDTO = profileService.followUser("test01", authMemberDTO);
        assertEquals(profileDTO.getUsername(), "test01");
        assertEquals(profileDTO.getBio(), "bio1");
        assertEquals(profileDTO.getImage(),"image1");
        assertEquals(profileDTO.isFollowing(),true);
    }

    @Test
    @DisplayName("UnFollow 테스트")
    public void unfollow_test() {
        //given
        //test02가 test01를 UnFollow 한다.
        AuthMemberDTO authMemberDTO = new AuthMemberDTO(2l,"test02@realworld.com","1111",new ArrayList<>());

        ProfileDTO mockProfileDTO = ProfileDTO.builder()
                .username("test01")
                .bio("bio1")
                .image("image1")
                .following(false)
                .build();

        Member test01 = Member.builder()
                .id(1l)
                .password("1111")
                .username("test01")
                .email("test01@realworld.com")
                .build();

        Follow follow = Follow.builder().build();

        when(memberRepository.findByUsername("test01")).thenReturn(Optional.of(test01));
        when(profileRepository.findByMemberIdAndFollowUsername(anyLong(),anyLong())).thenReturn(Optional.of(follow));
        when(profileQueryRepository.getProfile(anyLong(),any())).thenReturn(mockProfileDTO);


        //when
        ProfileDTO profileDTO = profileService.unFollowUser("test01", authMemberDTO);

        //then
        assertEquals(profileDTO.getUsername(), "test01");
        assertEquals(profileDTO.getBio(), "bio1");
        assertEquals(profileDTO.getImage(),"image1");
        assertEquals(profileDTO.isFollowing(),false);

    }

}