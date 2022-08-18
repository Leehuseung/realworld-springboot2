package com.kr.realworldspringboot2.member;


import com.kr.realworldspringboot2.profile.Follow;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;
    @Nullable
    private String bio;
    @Nullable
    private String image;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private List<MemberRole> roles = new ArrayList<>();

    @OneToMany(mappedBy = "followMember")
    @Builder.Default
    private Set<Follow> followee = new HashSet<>();

    public boolean isFollowee(Member loginMember) {
        Follow follow = Follow.builder()
                .member(loginMember)
                .followMember(this)
                .build();

        return followee.contains(follow);
    }

    public AuthorDTO toAuthorDTO(Member loginMember){
        return AuthorDTO.builder()
                .username(this.username)
                .bio(this.bio)
                .image(this.image)
                .following(isFollowee(loginMember))
                .build();
    }

}
