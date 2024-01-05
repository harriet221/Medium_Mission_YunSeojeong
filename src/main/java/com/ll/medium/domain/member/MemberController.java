package com.ll.medium.domain.member;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String join(MemberJoinForm memberJoinForm) {
        return "join_form";
    }

    @Data
    public static class MemberJoinForm {
        @NotBlank(message = "ID를 입력하세요.")
        @Size(min = 3, max = 25)
        private String username;

        @NotBlank(message = "비밀번호를 입력하세요.")
        private String password;

        @NotBlank(message = "비밀번호를 확인하세요.")
        private String passwordConfirm;

        private boolean isPaid = false;
    }

    @PostMapping("/join")
    public String join(@Valid MemberJoinForm memberJoinForm,
                       BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "join_form";
        }

        if(!memberJoinForm.password.equals(memberJoinForm.passwordConfirm)) {
            bindingResult.rejectValue("passwordConfirm", "passwordInCorrect",
                    "동일한 비밀번호를 적어주세요.");
            return "join_form";
        }

        try {
            memberService.create(memberJoinForm.username, memberJoinForm.password);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("joinFailed", "중복된 아이디 입니다.");
            return "join_form";
        } catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("joinFailed", e.getMessage());
            return "join_form";
        }
        return "redirect:/";
    }

    @GetMapping("login")
    public String login() {
        return "login_form";
    }
}
