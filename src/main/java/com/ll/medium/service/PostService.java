package com.ll.medium.service;

import com.ll.medium.domain.Post;
import com.ll.medium.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> recentList() {
        return postRepository.findFirst30ByOrderByCreatedDateDesc();
    }

    public List<Post> publishedList(){
        return postRepository.findByIsPublishedTrue();
    }

    public List<Post> myList(Long memberId) {
        return postRepository.findMyPosts(memberId);
    }

    public Optional<Post> findPost(Long postId){
        return postRepository.findById(postId);
    }
}
