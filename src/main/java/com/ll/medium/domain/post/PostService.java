package com.ll.medium.domain.post;

import com.ll.medium.domain.member.Member;
import com.ll.medium.global.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public Page<Post> getRecent(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        sorts.add(Sort.Order.asc("title"));
        Pageable pageable = PageRequest.of(page, 15, Sort.by(sorts));
        return this.postRepository.findAll(pageable);
    }

    public Page<Post> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        sorts.add(Sort.Order.asc("title"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        return this.postRepository.findAll(pageable);
    }

    public Page<Post> getMyList(int page, Member author) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        sorts.add(Sort.Order.asc("title"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.postRepository.findByAuthor(pageable, author);
    }

    public Page<Post> getYourList(int page, Member author) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        sorts.add(Sort.Order.asc("title"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.postRepository.findByAuthor(pageable, author);
    }

    public Post getPost(Integer id) {
        Optional<Post> post = this.postRepository.findById(id);
        if(post.isPresent()) {
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    public void writePost(String title, String content, Member author, Boolean isPub) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCreateDate(LocalDateTime.now());
        post.setAuthor(author);
        post.setIsPublished(isPub);
        this.postRepository.save(post);
    }

    public void modifyPost(Post post, String title, String content, Boolean isPub) {
        post.setTitle(title);
        post.setContent(content);
        post.setModifyDate(LocalDateTime.now());
        post.setIsPublished(isPub);
        this.postRepository.save(post);
    }

    public void deletePost(Post post) {
        this.postRepository.delete(post);
    }
}
