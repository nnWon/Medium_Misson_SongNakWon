package com.ll.medium.controller;

import com.ll.medium.config.security.CustomUserDetails;
import com.ll.medium.domain.Comment;
import com.ll.medium.domain.Member;
import com.ll.medium.domain.Post;
import com.ll.medium.dto.CommentForm;
import com.ll.medium.dto.PostWriteFormDto;
import com.ll.medium.service.CommentService;
import com.ll.medium.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

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


    @GetMapping("/post/{postId}/comment/{commentId}/modify")
    public String modifyCommentForm(CommentForm commentForm, @PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId,
                                    @AuthenticationPrincipal CustomUserDetails user, HttpServletResponse response) throws IOException {

        //Todo: 포스트없는 경우, 던질 예외 정하기
        Comment comment = commentService.findComment(commentId).orElseThrow();
        Member loginMember = user.getMember();

        //로그인한 유저와 댓글 작성자가 다르다면, 403 상태코드 전달
        if (!loginMember.isSameMember(comment.getMember())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "해당 댓글의 수정 권한이 없습니다.");
        }

        commentForm.setBody(comment.getBody());

        return "commentModifyForm";
    }

    @PostMapping("/post/{postId}/comment/{commentId}/modify")
    public String modify(@Validated CommentForm commentForm, BindingResult bindingResult,
                         @PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId,
                         @AuthenticationPrincipal CustomUserDetails user, HttpServletResponse response,
                         RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            return "commentModifyForm";
        }

        //Todo: 포스트없는 경우, 던질 예외 정하기
        Comment comment = commentService.findComment(commentId).orElseThrow();
        Member loginMember = user.getMember();

        //로그인한 유저와 댓글 작성자가 다르다면, 403 상태코드 전달
        if (!loginMember.isSameMember(comment.getMember())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "해당 댓글의 수정 권한이 없습니다.");
        }

        commentService.updateComment(commentId,commentForm.getBody());

        redirectAttributes.addAttribute("postId", postId);
        return "redirect:/post/{postId}";

    }
}
