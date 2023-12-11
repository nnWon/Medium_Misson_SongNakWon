package com.ll.medium.repository;

import com.ll.medium.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findFirst30ByOrderByCreatedDateDesc();

    List<Post> findByIsPublishedTrue();

    @Query("select p from Post p where p.member.id = :memberId")
    List<Post> findMyPosts(@Param("memberId") Long memberId);

}
