package com.teamy.mini.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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

}


//`id`        int NOT NULL ,
// `member_id` int NOT NULL ,
// `file_id`   int NULL ,
// `title`     varchar(50) NOT NULL ,
// `content`   varchar(1000) NULL ,
// `date_time` datetime NOT NULL ,
// `nickname`  varchar(45) NOT NULL ,