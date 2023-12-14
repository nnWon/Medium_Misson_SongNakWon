package com.ll.medium.service;

import com.ll.medium.domain.Comment;
import com.ll.medium.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment saveComment(Comment comment){
        return commentRepository.save(comment);
    }

    public Optional<Comment> findComment(Long commentId){
        return commentRepository.findById(commentId);
    }

    @Transactional
    public void updateComment(Long commentId, String body) {
        Comment comment = commentRepository.findById(commentId).get();
        comment.change(body); //더티체킹을 통해 업데이트
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
