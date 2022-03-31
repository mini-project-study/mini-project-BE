package com.teamy.mini.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Table(name = "article")
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "member_id", nullable = false)
    private int memberId;

    @Column(name = "nickname", nullable = false, length = 45)
    private String nickname;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File fileId;

    @Column(name = "date_time")
    @CreationTimestamp
    private Instant dateTime; //Date : 로컬 컴퓨터의 현재 날짜 및 시간 기준, Instant : 협정세계시(UTC) 기준

    public Article(int memberId, String nickname, String title, String content, File fileId) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.fileId = fileId;
    }
}