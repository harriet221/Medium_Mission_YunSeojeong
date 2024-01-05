package com.ll.medium.domain.post;

import com.ll.medium.domain.member.Member;
import com.ll.medium.domain.member.MemberRepository;
import com.ll.medium.global.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Page<Post> getRecent(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        sorts.add(Sort.Order.asc("title"));
        Pageable pageable = PageRequest.of(page, 15, Sort.by(sorts));
        return this.postRepository.findAllIsPublished(pageable);
    }

    public Page<Post> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        sorts.add(Sort.Order.asc("title"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        return this.postRepository.findAllIsPublished(pageable);
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
        return this.postRepository.findByAuthorIsPublished(pageable, author);
    }

    public Post getPost(Integer id) {
        Optional<Post> post = this.postRepository.findById(id);
        if(post.isPresent()) {
            if(post.get().getPaid()) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String currentUsername = authentication.getName();
                Optional<Member> member = memberRepository.findByUsername(currentUsername);
                if(member.isPresent() && member.get().isPaid()) {
                    return post.get();
                }
                if(!currentUsername.equals(post.get().getAuthor().getUsername())) {
                    post.get().setContent("※ 멤버십 포스트 입니다.");
                }
            }
            return post.get();

        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    public void writePost(String title, String content, Member author, Boolean isPub, Boolean paid) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCreateDate(LocalDateTime.now());
        post.setAuthor(author);
        post.setIsPublished(isPub);
        post.setPaid(paid);
        this.postRepository.save(post);
    }

    public void modifyPost(Post post, String title, String content, Boolean isPub, Boolean paid) {
        post.setTitle(title);
        post.setContent(content);
        post.setModifyDate(LocalDateTime.now());
        post.setIsPublished(isPub);
        post.setPaid(paid);
        this.postRepository.save(post);
    }

    public void deletePost(Post post) {
        this.postRepository.delete(post);
    }
}
