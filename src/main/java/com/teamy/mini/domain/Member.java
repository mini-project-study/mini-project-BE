package com.teamy.mini.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @Column(name = "nickname", nullable = false, length = 45)
    private String nickname;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    public Member(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
}
