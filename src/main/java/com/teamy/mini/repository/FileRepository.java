package com.teamy.mini.repository;

import com.teamy.mini.domain.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Slf4j
@RequiredArgsConstructor
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
