package com.teamy.mini.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "profile")
@NoArgsConstructor
public class Profile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @Column(name = "introduction")
    String introduction;

    public Profile(Member member, File file, String introduction) {
        this.member = member;
        this.file = file;
        this.introduction = introduction;
    }
}
