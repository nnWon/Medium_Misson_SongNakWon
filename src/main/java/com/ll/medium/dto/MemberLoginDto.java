package com.ll.medium.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginDto {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
