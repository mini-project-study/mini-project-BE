package com.teamy.mini.service;

import com.teamy.mini.domain.Article;
import com.teamy.mini.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public void registerArticle(Article article) {
        articleRepository.saveArticle(article);
    }

    public List<Map<String, String>> retrieveArticleList() {
        return articleRepository.findAllArticleList();
    }

    public Map<String, Object> retrieveByArticleId(int articleId) {
        return articleRepository.findByArticleId(articleId);
    }

    public void removeArticle(int articleId) {
        articleRepository.deleteArticle(articleId);
    }

    public void modifyArticle(Article article, int articleId) {
        articleRepository.updateArticle(article, articleId);
    }
}
