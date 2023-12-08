package com.ll.medium.domain;

import com.ll.medium.domain.base.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    private boolean isPublished;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
