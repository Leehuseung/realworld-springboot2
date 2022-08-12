package com.kr.realworldspringboot2.profile;

import com.kr.realworldspringboot2.member.Member;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOLLOW_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "FOLLOW_MEMBER_ID")
    private Member followMember;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follow follow = (Follow) o;
        return Objects.equals(member, follow.member) && Objects.equals(followMember, follow.followMember);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, followMember);
    }
}
