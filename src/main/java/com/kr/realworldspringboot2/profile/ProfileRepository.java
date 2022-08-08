package com.kr.realworldspringboot2.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Follow,Long> {
    @Query("select f from Follow f where f.member.id = :memberId and f.followMember.id = :followMemberId")
    Optional<Follow> findByMemberIdAndFollowUsername(Long memberId,Long followMemberId);
}
