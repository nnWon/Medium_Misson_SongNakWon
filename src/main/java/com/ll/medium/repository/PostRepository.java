package com.ll.medium.repository;

import com.ll.medium.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findFirst30ByOrderByCreatedDateDesc();

    List<Post> findByIsPublishedTrueOrderByCreatedDateDesc();

    @Query("select p from Post p where p.member.id = :memberId order by p.createdDate desc")
    List<Post> findPostsByMemberId(@Param("memberId") Long memberId);

}
