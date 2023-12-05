package com.ll.medium.domain.post.repository;

import com.ll.medium.domain.post.entity.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository {
    private final List<Post> postList = new ArrayList<>();

    public Post save(Post post) {
        if (post.getId() == null) {
            post.setId(postList.size() + 1);
            post.setCreateDate(LocalDateTime.now());
        }
        postList.add(post);
        return post;
    }

    public List<Post> getList() {
        return postList;
    }
}
