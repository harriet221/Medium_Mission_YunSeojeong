package com.ll.medium.domain.post;

import com.ll.medium.domain.comment.CommentController;
import com.ll.medium.domain.member.Member;
import com.ll.medium.domain.member.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/main")
    public String showMain(Model model,
                           @RequestParam(value="page", defaultValue="0") int page) {
        Page<Post> paging = this.postService.getRecent(page);
        model.addAttribute("paging", paging);
        return "main";
    }

    @GetMapping("/list")
    public String showList(Model model,
                           @RequestParam(value="page", defaultValue="0") int page) {
        Page<Post> paging = this.postService.getList(page);
        model.addAttribute("paging", paging);
        return "post_list";
    }

    @GetMapping("/myList")
    public String showMyList(Model model,
                             @RequestParam(value="page", defaultValue="0") int page,
                             Principal principal) {
        if(principal == null) {

        }
        Member me = this.memberService.getUser(principal.getName());
        Page<Post> paging = this.postService.getMyList(page, me);
        model.addAttribute("paging", paging);
        return "post_list";
    }

    @GetMapping("/b/{author}")
    public String showYourList(Model model,
                               @PathVariable("author") String author,
                               @RequestParam(value="page", defaultValue="0") int page) {
        Member user = this.memberService.getUser(author);
        Page<Post> paging = this.postService.getYourList(page, user);
        model.addAttribute("paging", paging);
        return "post_list";
    }

    @GetMapping("/b/{author}/{id}")
    public String showYourPost(Model model,
                               @PathVariable("author") String author,
                               @PathVariable("id") Integer id,
                               CommentController.CommentForm commentForm) {
        Member user = this.memberService.getUser(author);
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "post_detail";
    }

    @GetMapping("/{id}")
    public String detail(Model model,
                         @PathVariable("id") Integer id,
                         CommentController.CommentForm commentForm) {
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "post_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String writePost(PostForm postForm) {
        return "post_form";
    }

    @Data
    public static class PostForm {
        @NotBlank(message="제목을 입력하세요.")
        @Size(max=200)
        private String title;

        @NotBlank(message="내용을 입력하세요.")
        private String content;

        private Boolean isPublished;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String writePost(@Valid PostForm postForm,
                            BindingResult bindingResult,
                            Principal principal) {
        if(bindingResult.hasErrors()) {
            return "post_form";
        }
        Member member = this.memberService.getUser(principal.getName());
        this.postService.writePost(postForm.title, postForm.content, member, postForm.isPublished);
        return "redirect:/post/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String modifyPost(Model model,
                             PostForm postForm,
                             @PathVariable("id") Integer id,
                             Principal principal) {
        Post post = this.postService.getPost(id);
        if(!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());
        model.addAttribute("postForm", postForm);
        model.addAttribute("post", post);
        return "post_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/modify")
    public String modifyPost(@Valid PostForm postForm,
                             BindingResult bindingResult,
                             Principal principal,
                             @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "post_form";
        }
        Post post = this.postService.getPost(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }
        this.postService.modifyPost(post, postForm.getTitle(), postForm.getContent(), postForm.getIsPublished());
        return String.format("redirect:/post/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/delete")
    public String deletePost(Principal principal,
                             @PathVariable("id") Integer id) {
        Post post = this.postService.getPost(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }
        this.postService.deletePost(post);
        return "redirect:/post/list";
    }
}
