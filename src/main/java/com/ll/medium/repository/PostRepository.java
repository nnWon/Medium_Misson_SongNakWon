package com.ll.medium.repository;

import com.ll.medium.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findFirst30ByOrderByCreatedDateDesc();

    List<Post> findByIsPublishedTrue();

}
