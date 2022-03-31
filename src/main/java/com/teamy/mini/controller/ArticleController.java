package com.teamy.mini.controller;

import com.teamy.mini.domain.Article;
import com.teamy.mini.domain.File;
import com.teamy.mini.domain.ResponseMessage;
import com.teamy.mini.repository.ArticleRepository;
import com.teamy.mini.repository.FileRepository;
import com.teamy.mini.security.MemberAccount;
import com.teamy.mini.service.ArticleService;
import com.teamy.mini.utill.FileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;
    private final FileManager fileManager;
    private final FileRepository fileRepository;

    @PostMapping("article")
    public ResponseEntity<ResponseMessage> writeArticle(
            @RequestParam("nickname") String nickname,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "file", required = false) MultipartFile multipartFile) { //@RequestBody -> json으로 들어오는 바디 데이터를 파싱해주는거라고 한다 (form-data 쓸 때는 빼줘야 함)

        try {
            int memberId = ((MemberAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMember().getId();

            File file = fileManager.uploadFile(multipartFile);
            fileRepository.saveFile(file);

            Article article = new Article(memberId, nickname, title, content, file);
            articleService.registerArticle(article);
            return new ResponseEntity<>(new ResponseMessage(true, "게시글 등록 성공", "", null), HttpStatus.OK);
        } catch (Exception e) {
            log.info("null 값 체크 필요");
            throw new PropertyValueException("PropertyValueException", "Article", "");
        }
    }

    @GetMapping("/articles/{articleId}")
    public ResponseEntity<ResponseMessage> findOneArticle(@PathVariable("articleId") int articleId) {
        try {
            Map<String, Object> map = articleService.retrieveByArticleId(articleId);
            return new ResponseEntity<>(new ResponseMessage(true, "게시글 단일 조회", "", map), HttpStatus.OK);
        } catch (Exception e) {
            log.info("존재하지 않는 게시글 번호");
            throw new NullPointerException("NullPointerException");
        }
    }

    @GetMapping("/articles")
    public ResponseEntity<ResponseMessage> findAllArticle() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, String>> articleList = articleService.retrieveArticleList();

        map.put("currentPage", 0);
        map.put("totalPage", articleList.size());
        map.put("articleList", articleList);

        return new ResponseEntity<>(new ResponseMessage(true, "게시글 전체 목록 조회", "", map), HttpStatus.OK);
    }

    //게시글 수정
    @PutMapping("/articles/{articleId}")
    public ResponseEntity<ResponseMessage> updateArticle(@RequestBody Article article, @PathVariable("articleId") int articleId) {
        articleService.modifyArticle(article, articleId);
        Map<String, Object> map = articleService.retrieveByArticleId(articleId);
        return new ResponseEntity<>(new ResponseMessage(true, "게시글 수정", "", map), HttpStatus.OK);
    }

    //게시글 삭제
    @PostMapping("/articles/{articleId}")
    public ResponseEntity<ResponseMessage> deleteArticle(@PathVariable("articleId") int articleId) {
        try {
            articleService.removeArticle(articleId);
            return new ResponseEntity<>(new ResponseMessage(true, "게시글 삭제", "", null), HttpStatus.OK);
        } catch (Exception e) {
            log.info("이미 삭제된 게시글 번호");
            throw new IllegalArgumentException("IllegalArgumentException");
        }
    }

}


