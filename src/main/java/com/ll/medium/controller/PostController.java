package com.ll.medium.controller;

import com.ll.medium.domain.Post;
import com.ll.medium.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping()
    public String LatestPosts(Model model){
        List<Post> recentPosts = postService.recentList();
        model.addAttribute("recentPosts", recentPosts);
        return "recentPostList";
    }

    @GetMapping("/list")
    public String posts(Model model){
        List<Post> publishedPosts = postService.publishedList();
        model.addAttribute("posts", publishedPosts);
        return "postList";
    }
}
