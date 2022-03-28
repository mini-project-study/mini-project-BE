package com.teamy.mini.service;

import com.teamy.mini.domain.Article;
import com.teamy.mini.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public void registerArticle(Article article) {
        articleRepository.saveArticle(article);
    }
}
