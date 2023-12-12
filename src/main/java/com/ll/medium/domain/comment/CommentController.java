package com.ll.medium.domain.comment;

import com.ll.medium.domain.member.Member;
import com.ll.medium.domain.member.MemberService;
import com.ll.medium.domain.post.Post;
import com.ll.medium.domain.post.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {
    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;

    @Data
    public static class CommentForm {
        @NotBlank(message="내용을 입력하세요.")
        private String content;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/write")
    public String writeComment(Model model,
                               @PathVariable("id") Integer id,
                               @Valid CommentForm commentForm,
                               BindingResult bindingResult,
                               Principal principal) {
        Post post = this.postService.getPost(id);
        Member member = this.memberService.getUser(principal.getName());
        if(bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "post_detail";
        }
        this.commentService.writeComment(post, commentForm.content, member);
        return String.format("redirect:/post/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String commentModify(CommentForm commentForm,
                                @PathVariable("id") Integer id,
                                Principal principal) {
        Comment comment = this.commentService.getComment(id);
        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }
        commentForm.setContent(comment.getContent());
        return "comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/modify")
    public String commentModify(@Valid CommentForm commentForm,
                               BindingResult bindingResult,
                               @PathVariable("id") Integer id,
                               Principal principal) {
        if (bindingResult.hasErrors()) {
            return "comment_form";
        }
        Comment comment = this.commentService.getComment(id);
        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }
        this.commentService.modifyComment(comment, commentForm.getContent());
        return String.format("redirect:/post/%s", comment.getPost().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/delete")
    public String commentDelete(Principal principal,
                                @PathVariable("id") Integer id) {
        Comment comment = this.commentService.getComment(id);
        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }
        this.commentService.delete(comment);
        return String.format("redirect:/post/%s", comment.getPost().getId());
    }
}
