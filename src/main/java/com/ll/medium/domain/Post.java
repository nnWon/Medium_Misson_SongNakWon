package com.ll.medium.domain;

import com.ll.medium.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    private boolean isPublished;

    @ColumnDefault("0")
    private int views;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Post(String title, String body, Boolean isPublished) {
        this.title = title;
        this.body = body;
        this.isPublished = isPublished;
    }

    public void addMember(Member member) {
        this.member = member;
    }

    public void change(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public void increaseViews() {
        this.views += 1;
    }
}
