package com.ll.medium.service;

import com.ll.medium.domain.Post;
import com.ll.medium.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> recentList() {
        return postRepository.findFirst30ByOrderByCreatedDateDesc();
    }
}
