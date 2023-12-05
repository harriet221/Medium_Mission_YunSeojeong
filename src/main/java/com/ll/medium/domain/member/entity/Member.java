package com.ll.medium.domain.member.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private Long id;
    private String username;
    private String password;
    private String email;
}
