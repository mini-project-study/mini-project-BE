package com.teamy.mini.repository;

import com.teamy.mini.domain.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Repository
public class ArticleRepository{

    private final EntityManager em;

    //게시판 작성
    public void saveArticle(Article article) {
        em.persist(article);
    }

    //게시판 조회
    public List<Map<String, String>> findArticleList() { //a.id, a.nickname, a.memberId, a.title, a.dateTime
        List<Article> list = em.createQuery("SELECT a FROM Article a", Article.class).getResultList();
        List<Map<String, String>> articleList = new ArrayList<>();

        for(Article article : list) {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("id", String.valueOf(article.getId()));
            map.put("nickname", article.getNickname());
            map.put("memberId", String.valueOf(article.getMemberId()));
            map.put("title", article.getTitle());
            map.put("dateTime", String.valueOf(article.getDateTime()));

            articleList.add(map);
        }
        return articleList;
    }



    //게시판 삭제

    //게시판 수정
}
