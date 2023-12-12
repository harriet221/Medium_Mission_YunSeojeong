package com.ll.medium.domain.post;

import com.ll.medium.domain.comment.Comment;
import com.ll.medium.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    private LocalDateTime modifyDate;

    @ManyToOne
    private Member author;

    public Post() {}

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
