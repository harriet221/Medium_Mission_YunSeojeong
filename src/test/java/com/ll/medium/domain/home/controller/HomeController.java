package com.ll.medium.domain.home.home.controller;

import com.ll.medium.domain.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class HomeController {

    @Autowired
    private PostService postService;

//    @Test
//    void testJpa() {
//        for(int i = 1; i <= 100; i++) {
//            String title = String.format("[%03d]번 테스트 데이터", i);
//            String content = "내용 없음";
//            this.postService.writePost(title, content, null);
//        }
//    }

}
