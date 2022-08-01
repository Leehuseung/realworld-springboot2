package com.kr.realworldspringboot2.member;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.LOAD;


public interface MemberRepository extends JpaRepository<Member,Long> {

    @EntityGraph(attributePaths = {"roles"}, type = LOAD)
    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = {"roles"}, type = LOAD)
    Optional<Member> findByUsername(String username);

}
