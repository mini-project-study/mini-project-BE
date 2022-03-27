package com.teamy.mini.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Table(name = "article")
@NoArgsConstructor
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member memberId;

    @Column(name = "nickname", nullable = false, length = 45)
    private String nickname;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private File fileId;

    @Column(name = "date_time", nullable = false)
    private Instant dateTime; //Date는 로컬 컴퓨터의 현재 날짜와 시간을 기준으로, Instant는 협정세계시(UTC)를 기준

}


//`id`        int NOT NULL ,
// `member_id` int NOT NULL ,
// `file_id`   int NULL ,
// `title`     varchar(50) NOT NULL ,
// `content`   varchar(1000) NULL ,
// `date_time` datetime NOT NULL ,
// `nickname`  varchar(45) NOT NULL ,