package com.ll.medium.domain.member;

import com.ll.medium.global.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(String username, String password) {
        Member user = new Member();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        this.memberRepository.save(user);
        return user;
    }

    public Member getUser(String username) {
        Optional<Member> siteMember = this.memberRepository.findByUsername(username);
        if (siteMember.isPresent()) {
            return siteMember.get();
        } else {
            throw new DataNotFoundException("member not found");
        }
    }

}
