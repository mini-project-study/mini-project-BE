package com.teamy.mini.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Getter
@Table(name="file")
@NoArgsConstructor
public class File {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name="original_file_name", nullable = false, length = 100)
    private String originalFileName;

    @Column(name="system_file_name", nullable = false, length = 100)
    private String systemFileName;

    @Column(name="file_size", nullable = false)
    private Long fileSize;

    @Column(name = "file_type", nullable = false, length = 20)
    private String fileType;

    @Column(name = "date_time")
    @CreationTimestamp
    private Instant datetime;

    public File(String originalFileName, String systemFileName, String fileType, Long fileSize) {
        this.originalFileName = originalFileName;
        this.systemFileName = systemFileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
    public File(String originalFileName, String systemFileName, Long fileSize) {
        this.originalFileName = originalFileName;
        this.systemFileName = systemFileName;
        this.fileSize = fileSize;
    }
}