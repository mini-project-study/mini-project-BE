package com.teamy.mini.repository;

import com.teamy.mini.domain.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Repository
public class ArticleRepository{

    private final EntityManager em;

    //게시글 작성
    public void saveArticle(Article article) {
        em.persist(article);
    }

    //게시글 상세조회
    public Map<String, Object> findByArticleId(int articleId) {
        Map<String, Object> map = new LinkedHashMap<>();

        Article list = em.find(Article.class, articleId);

        map.put("id", list.getId());
        map.put("nickname", list.getNickname());
        map.put("memberId", list.getMemberId());
        map.put("title", list.getTitle());
        map.put("content", list.getContent());
        map.put("fileId", list.getFileId());
        map.put("dateTime", list.getDateTime());

        return map;
    }

    //전체 게시글 목록 조회
    public List<Map<String, String>> findAllArticleList() { //a.id, a.nickname, a.memberId, a.title, a.dateTime
        List<Article> list = em.createQuery("SELECT a FROM Article a", Article.class).getResultList();
        return findArticleList(list);
    }

    //회원별 게시글 목록 조회
    public List<Map<String, String>> findArticleListByMemberId(int memberId) {
        List<Article> list = em.createQuery("SELECT a FROM Article a WHERE a.memberId = :memberId", Article.class).setParameter("memberId", memberId).getResultList();
        return findArticleList(list);
    }

    //게시글 목록 조회
    public List<Map<String, String>> findArticleList(List<Article> list) {
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

    //게시글 수정
    public void updateArticle(Article article, int articleId) {
        em.createQuery("UPDATE Article a SET a.title = :title, a.content = :content, a.fileId = :fileId WHERE a.id = :articleId")
                .setParameter("title", article.getTitle())
                .setParameter("content", article.getContent())
                .setParameter("fileId", article.getFileId())
                .setParameter("articleId", articleId)
                .executeUpdate();
    }

    //게시글 삭제
    public void deleteArticle(int articleId) {
        Article a = em.find(Article.class, articleId);
        em.remove(a);
    }


}
