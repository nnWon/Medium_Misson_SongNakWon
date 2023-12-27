package com.ll.medium.repository;

import com.ll.medium.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p left join fetch p.member m left join fetch p.comments c order by p.createdDate desc limit 30")
    List<Post> findFirst30ByOrderByCreatedDateDesc();

    @Query("select p from Post p left join fetch p.member m left join fetch p.comments c where p.isPublished = true order by p.createdDate desc")
    List<Post> findByIsPublishedTrueOrderByCreatedDateDesc();

    @Query("select p from Post p where p.member.id = :memberId order by p.createdDate desc")
    List<Post> findPostsByMemberId(@Param("memberId") Long memberId);

    @Query("select p from Post p left join fetch p.comments where p.id = :postId")
    Post findPostFetchJoinComment(@Param("postId") Long postId);
}
