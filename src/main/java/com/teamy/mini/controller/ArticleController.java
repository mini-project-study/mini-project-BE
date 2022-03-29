package com.teamy.mini.controller;

import com.teamy.mini.domain.Article;
import com.teamy.mini.domain.ResponseMessage;
import com.teamy.mini.repository.ArticleRepository;
import com.teamy.mini.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    @PostMapping("article")
    public ResponseEntity<ResponseMessage> writeArticle(@RequestBody Article article) {
        articleService.registerArticle(article);
        return new ResponseEntity<>(new ResponseMessage(true, "게시글 등록 성공", "", null), HttpStatus.OK);
    }

    @GetMapping("/articles")
    public ResponseEntity<ResponseMessage> findAllArticle() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, String>> articleList = articleService.retrieveArticleList();

        map.put("currentPage", 0);
        map.put("totalPage", articleList.size());
        map.put("articleList", articleList);

        return new ResponseEntity<>(new ResponseMessage(true, "게시글 전체 목록 조회 성공", "", map), HttpStatus.OK);
    }                                                                                                             
}
