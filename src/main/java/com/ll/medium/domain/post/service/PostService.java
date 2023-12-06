package com.ll.medium.domain.post.service;

import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public Post write(String title, String content) {
        Post post = new Post(title, content);
        return post;
    }

    public List<Post> getList() {
        return postRepository.getList();
    }

    public List<Post> getRecentList() {
        return postRepository.getRecentList();
    }
}
