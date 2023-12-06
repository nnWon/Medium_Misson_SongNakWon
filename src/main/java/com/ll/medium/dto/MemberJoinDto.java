package com.ll.medium.dto;

import com.ll.medium.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinDto {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordConfirm;
    @NotEmpty
    @Email
    private String email;


    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
    }

    public boolean isNotMatchPasswords() {
        return !password.equals(passwordConfirm);
    }
}
