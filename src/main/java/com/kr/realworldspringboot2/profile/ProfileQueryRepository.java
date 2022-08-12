package com.kr.realworldspringboot2.profile;

import com.kr.realworldspringboot2.member.QMember;
import com.kr.realworldspringboot2.security.AuthMemberDTO;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.kr.realworldspringboot2.member.QMember.member;
import static com.kr.realworldspringboot2.profile.QFollow.follow;

@AllArgsConstructor
@Repository
public class ProfileQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public  ProfileDTO getProfile(long id, AuthMemberDTO authMemberDTO) {
        ProfileDTO profile = jpaQueryFactory
                .select(Projections.constructor(
                        ProfileDTO.class,
                        member.username,
                        member.bio,
                        member.image,
                        ExpressionUtils.as(JPAExpressions
                                .select(follow)
                                .from(follow)
                                .where(loginEq(authMemberDTO),
                                        follow.followMember.eq(member))
                                .fetchAll().exists()
                                ,"following")
                ))
                .from(member)
                .where(member.id.eq(id))
                .fetchOne();
        return profile;
    }

    private Predicate loginEq(AuthMemberDTO authMemberDTO) {
        return authMemberDTO == null ? follow.member.id.eq(-1l) : follow.member.id.eq(authMemberDTO.getId());
    }
}
