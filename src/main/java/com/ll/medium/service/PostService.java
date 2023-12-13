package com.ll.medium.service;

import com.ll.medium.domain.Member;
import com.ll.medium.domain.Post;
import com.ll.medium.repository.MemberRepository;
import com.ll.medium.repository.PostRepository;
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
    private final MemberRepository memberRepository;

    public List<Post> recentList() {
        return postRepository.findFirst30ByOrderByCreatedDateDesc();
    }

    public List<Post> publishedList() {
        return postRepository.findByIsPublishedTrueOrderByCreatedDateDesc();
    }

    public List<Post> findPosts(Long memberId) {
        return postRepository.findPostsByMemberId(memberId);
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

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public List<Post> findPostsByAnotherMember(String username) {
        //Todo: 해당 멤버없는 경우, 던질 예외 정하기
        Member member = memberRepository.findByUsername(username).orElseThrow();
        return postRepository.findPostsByMemberId(member.getId());
    }

    @Transactional
    public void increaseViews(Long postId) {
        Post post = postRepository.findById(postId).get();
        post.increaseViews(); //더티체킹을 통해 업데이트
    }
}
