package com.teamy.mini.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
public class File {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "original_file_name", nullable = false, length = 100)
    private String originalFileName;

    @Column(name = "system_file_name", nullable = false, length = 100)
    private String systemFileName;

    @Column(name = "file_size", nullable = false, length = 20)
    private long fileSize;

    @Column(name = "file_type", nullable = false, length = 20)
    private String fileType;

    @Column(name = "date_time", nullable = false)
    private Instant dateTime;

}


// `id`                 int NOT NULL ,
// `original_file_name` varchar(100) NOT NULL ,
// `system_file_name`   varchar(100) NOT NULL ,
// `file_size`          bigint(20) NOT NULL ,
// `file_type`          varchar(20) NOT NULL ,
// `date_time`         datetime NOT NULL ,