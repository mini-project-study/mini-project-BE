package com.teamy.mini.controller;

import com.teamy.mini.domain.Article;
import com.teamy.mini.domain.ResponseMessage;
import com.teamy.mini.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("article")
    public ResponseEntity<ResponseMessage> writeArticle(@RequestBody Article article) {
        articleService.registerArticle(article);
        return new ResponseEntity<>(new ResponseMessage(true, "게시글 등록 성공", "", null), HttpStatus.OK);
    }

}
