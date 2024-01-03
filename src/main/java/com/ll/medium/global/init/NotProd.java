package com.ll.medium.global.init;

import com.ll.medium.domain.member.Member;
import com.ll.medium.domain.member.MemberRepository;
import com.ll.medium.domain.member.MemberService;
import com.ll.medium.domain.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.stream.IntStream;

@Configuration
@Profile("!prod")
@RequiredArgsConstructor
public class NotProd {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PostService postService;

    @Bean
    public ApplicationRunner initNotProd() {
        return args -> {
            // 한 번 실행했으면 다음부턴 안 해도 됨
            // 완전 초기화 하려면 application.yml에서 ddl-auto: update 를 create로 바꿔서 한 번 실행할 것
            if (memberRepository.findByUsername("user99").isPresent()) return;

            Member memberUser99 = memberService.create("user99", "1234");

            postService.writePost( "기본 샘플 1", "내용 없음", memberUser99, true, false);
            postService.writePost( "기본 샘플 2", "내용 없음", memberUser99, true, false);
            postService.writePost( "기본 샘플 3", "내용 있음", memberUser99, false, false);
            postService.writePost( "기본 샘플 4", "내용 있음", memberUser99, false, false);

            IntStream.rangeClosed(1, 100).forEach(i -> {
                postService.writePost( "멤버십 샘플 "+i, "내용 "+i, memberUser99, true, true);
            });
        };
    }
}