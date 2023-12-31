package com.ll.medium.domain;

import com.ll.medium.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(value = EnumType.STRING)
    private Set<MemberRole> roleSet = new HashSet<>();

    private boolean isPaid;

    public Member(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public boolean isSameMember(Member member){
        return this.id.equals(member.getId());
    }

    public void convertToMembershipMember(){
        this.isPaid = true;
    }

    public void changeToEncodedPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void addRole(MemberRole memberRole) {
        this.roleSet.add(memberRole);
    }
}
