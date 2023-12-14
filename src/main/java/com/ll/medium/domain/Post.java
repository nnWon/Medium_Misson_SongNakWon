package com.ll.medium.domain;

import com.ll.medium.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@ToString
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

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

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
