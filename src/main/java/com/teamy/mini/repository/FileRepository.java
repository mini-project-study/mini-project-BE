package com.teamy.mini.repository;

import com.teamy.mini.domain.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Repository
public class FileRepository {
    private final EntityManager em;

    public void saveFile(File file) {
        em.persist(file);
    }

    public File findFile(int id) {
        return em.find(File.class, id);
    }


}
