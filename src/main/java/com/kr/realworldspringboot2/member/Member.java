package com.kr.realworldspringboot2.member;


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
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    @NotNull
    private String email;
    @NotNull
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

    public void addMemberRole(MemberRole memberRole) {
        roles.add(memberRole);
    }


}
