package com.ll.medium.domain.post.repository;

import com.ll.medium.domain.post.entity.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Post> getRecentList() {
        List<Post> sortedList = postList.stream()
                .sorted(Comparator.comparing(Post::getCreateDate).reversed())
                .collect(Collectors.toList());

        List<Post> recentList = sortedList.stream()
                .limit(30)
                .collect(Collectors.toList());

        return recentList;
    }
}
