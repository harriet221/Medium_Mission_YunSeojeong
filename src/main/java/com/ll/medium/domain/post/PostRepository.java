package com.ll.medium.domain.post;

import com.ll.medium.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Post findByTitle(String title);
    Post findByTitleAndContent(String title, String content);
    List<Post> findByTitleLike(String title);
    Page<Post> findAll(Pageable pageable);
    // Page<Post> findAllByIsPublished(Pageable pageable);
    Page<Post> findByAuthor(Pageable pageable, Member author);
    // Post findByIdGreaterThanEqual(Integer id); // 최신 30개 이걸로 가능?
    // Post findBySubjectOrderByCreateDateAsc(String subject); // 아님 이걸 응용?

}
