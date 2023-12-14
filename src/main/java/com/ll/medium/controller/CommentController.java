package com.ll.medium.controller;

import com.ll.medium.config.security.CustomUserDetails;
import com.ll.medium.domain.Comment;
import com.ll.medium.domain.Post;
import com.ll.medium.dto.CommentForm;
import com.ll.medium.service.CommentService;
import com.ll.medium.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@PreAuthorize("isAuthenticated()")
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/post/{postId}/comment/write")
    public String createComment(@PathVariable Long postId, @AuthenticationPrincipal CustomUserDetails user,
                                @Validated CommentForm commentForm, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Model model) {

        Post postWithComment = postService.findPostWithComment(postId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("post", postWithComment);
            return "postDetail";
        }
        Comment comment = commentForm.toEntity(user.getMember(), postWithComment);
        commentService.saveComment(comment);

        redirectAttributes.addAttribute("postId", postId);
        return "redirect:/post/{postId}";
    }
}
