package com.ll.medium.dto;

import com.ll.medium.domain.Comment;
import com.ll.medium.domain.Member;
import com.ll.medium.domain.Post;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
    @NotEmpty
    private String body;

    public Comment toEntity(Member member, Post post) {
        return new Comment(member, post, this.body);
    }
}
