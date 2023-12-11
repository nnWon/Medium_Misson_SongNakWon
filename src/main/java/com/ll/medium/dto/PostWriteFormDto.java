package com.ll.medium.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostWriteFormDto {
    @NotEmpty
    private String title;
    @NotEmpty
    private String body;

    private Boolean isPublished;
}
