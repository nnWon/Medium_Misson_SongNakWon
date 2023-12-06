package com.ll.medium.dto;

import com.ll.medium.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinDto {

    private String username;
    private String password;
    private String passwordConfirm;
    private String email;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}
