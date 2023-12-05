package com.ll.medium.domain.member.controller;

import com.ll.medium.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/join")
    public String join() {
        return "join_form";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

}
