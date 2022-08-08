package com.kr.realworldspringboot2.profile;

import com.kr.realworldspringboot2.security.AuthMemberDTO;

public interface ProfileService {
    ProfileDTO getProfile(String username, AuthMemberDTO authMemberDTO);
}
