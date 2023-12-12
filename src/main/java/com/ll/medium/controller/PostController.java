package com.ll.medium.controller;

import com.ll.medium.config.security.CustomUserDetails;
import com.ll.medium.domain.Member;
import com.ll.medium.domain.Post;
import com.ll.medium.dto.PostWriteFormDto;
import com.ll.medium.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping()
    public String LatestPosts(Model model) {
        List<Post> recentPosts = postService.recentList();
        model.addAttribute("recentPosts", recentPosts);
        return "recentPostList";
    }

    @GetMapping("/list")
    public String posts(HttpServletRequest request, Model model) {
        List<Post> publishedPosts = postService.publishedList();
        model.addAttribute("posts", publishedPosts);
        model.addAttribute("url", request.getRequestURI());
        return "postList";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myList")
    public String myPosts(@AuthenticationPrincipal CustomUserDetails user, HttpServletRequest request, Model model) {
        List<Post> myPosts = postService.myList(user.getMember().getId());
        model.addAttribute("posts", myPosts);
        model.addAttribute("url", request.getRequestURI());
        return "postList";
    }

    @GetMapping("/{postId}")
    public String postDetail(@PathVariable("postId") Long postId, Authentication authentication, Model model) {
        //Todo: 포스트없는 경우, 던질 예외 정하기
        Post post = postService.findPost(postId).orElseThrow();

        //공개게시글이 아니고, 로그인하지 않은 사용자일 경우
        if (!post.isPublished() && (authentication == null || !authentication.isAuthenticated())) {
            return "redirect:/member/login";
        }

        model.addAttribute("post", post);
        return "postDetail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String writeForm(PostWriteFormDto postWriteFormDto, HttpServletRequest request, Model model) {
        model.addAttribute("url", request.getRequestURI());
        return "postWriteForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String write(@Validated PostWriteFormDto postWriteFormDto, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails user,
                        RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "postWriteForm";
        }

        Post post = postWriteFormDto.toEntity();
        post.addMember(user.getMember());

        Long savedPostId = postService.savePost(post);

        redirectAttributes.addAttribute("postId", savedPostId);

        return "redirect:/post/{postId}";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{postId}/modify")
    public String writeForm(PostWriteFormDto postWriteFormDto, @PathVariable("postId") Long postId, @AuthenticationPrincipal CustomUserDetails user,
                            HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

        //Todo: 포스트없는 경우, 던질 예외 정하기
        Post post = postService.findPost(postId).orElseThrow();
        Member loginMember = user.getMember();

        //로그인한 유저와 게시글 작성자가 다르다면, 403 상태코드 전달
        if (!loginMember.isSameMember(post.getMember())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "해당 게시글의 수정 권한이 없습니다.");
        }

        postWriteFormDto.setTitle(post.getTitle());
        postWriteFormDto.setBody(post.getBody());
        postWriteFormDto.setIsPublished(post.isPublished());

        model.addAttribute("url", request.getRequestURI());

        return "postWriteForm";

    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{postId}/modify")
    public String write(PostWriteFormDto postWriteFormDto, @PathVariable("postId") Long postId, @AuthenticationPrincipal CustomUserDetails user,
                        HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException {

        //Todo: 포스트없는 경우, 던질 예외 정하기
        Post post = postService.findPost(postId).orElseThrow();
        Member loginMember = user.getMember();

        //로그인한 유저와 게시글 작성자가 다르다면, 403 상태코드 전달
        if (!loginMember.isSameMember(post.getMember())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "해당 게시글의 수정 권한이 없습니다.");
        }

        postService.updatePost(postId, postWriteFormDto.getTitle(), postWriteFormDto.getBody());

        redirectAttributes.addAttribute("postId", postId);
        return "redirect:/post/{postId}";

    }
}
