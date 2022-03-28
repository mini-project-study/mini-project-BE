package com.teamy.mini.repository;

import com.teamy.mini.domain.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Repository
public class ArticleRepository {

    private final EntityManager em;

    //게시판 작성
    public void saveArticle(Article article) {
        em.persist(article);
    }

    //게시판 조회

    //게시판 삭제

    //게시판 수정
}
