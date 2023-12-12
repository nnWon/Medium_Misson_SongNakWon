package com.ll.medium.service;

import com.ll.medium.domain.Post;
import com.ll.medium.repository.PostRepository;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public List<Post> recentList() {
        return postRepository.findFirst30ByOrderByCreatedDateDesc();
    }

    public List<Post> publishedList() {
        return postRepository.findByIsPublishedTrueOrderByCreatedDateDesc();
    }

    public List<Post> myList(Long memberId) {
        return postRepository.findMyPosts(memberId);
    }

    public Optional<Post> findPost(Long postId) {
        return postRepository.findById(postId);
    }

    @Transactional
    public Long savePost(Post post) {
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional
    public void updatePost(Long postId, String title, String body) {

        Post post = postRepository.findById(postId).get();
        post.change(title, body); //더티체킹을 통해 업데이트
    }
}
