package com.kr.realworldspringboot2.security;

import com.kr.realworldspringboot2.member.Member;
import com.kr.realworldspringboot2.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> result = memberRepository.findByEmail(username);

        Member member = result.orElseThrow(() -> {
            throw new UsernameNotFoundException("Check Email");
        });

        AuthMemberDTO authMemberDTO = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
                                 .collect(Collectors.toSet()));
        authMemberDTO.setUsername(member.getUsername());
        return authMemberDTO;
    }
}
