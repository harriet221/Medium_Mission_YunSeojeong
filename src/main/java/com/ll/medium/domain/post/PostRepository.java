package com.ll.medium.domain.post;

import com.ll.medium.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

//    Post findByTitle(String title);
//    Post findByTitleAndContent(String title, String content);
//    List<Post> findByTitleLike(String title);
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByAuthor(Pageable pageable, Member author);

    @Query("select "
            + "distinct p "
            + "from Post p "
            + "where p.isPublished=true "
            + "and p.paid!=true ")
    Page<Post> findAllIsPublished(Pageable pageable);

    @Query("select "
            + "distinct p "
            + "from Post p "
            + "left outer join Member m1 on p.author=m1 "
            + "where p.isPublished=true "
            + "and (m1 = :user)")
    Page<Post> findByAuthorIsPublished(Pageable pageable, @Param("user") Member author);

}
