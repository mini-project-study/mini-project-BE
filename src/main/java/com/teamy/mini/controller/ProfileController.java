package com.teamy.mini.controller;

import com.teamy.mini.domain.File;
import com.teamy.mini.domain.Profile;
import com.teamy.mini.domain.ResponseMessage;
import com.teamy.mini.repository.ArticleRepository;
import com.teamy.mini.security.MemberAccount;
import com.teamy.mini.service.ArticleService;
import com.teamy.mini.service.MemberService;
import com.teamy.mini.utill.FileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProfileController {
    private final FileManager fileManager;
    private final MemberService memberService;
    private final ArticleRepository articleRepository;

    @PostMapping("filetest")
    public ResponseEntity<String> fileTest(@RequestParam("file") MultipartFile uploadFile) {
        log.info("------- : {} " , uploadFile.getOriginalFilename());
        File file = null;
        if(uploadFile != null) {
            file = fileManager.uploadFile(uploadFile);
            memberService.registerFile(file);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponseMessage> profileInquiry() {

        int memberId = ((MemberAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMember().getId();

        Map<String, Object> data = new HashMap<>();

        Profile profile = memberService.findProfile(memberId);
        data.put("nickname", profile.getMember().getNickname());
        data.put("file", profile.getFile().getSystemFileName());
        data.put("introduction", profile.getIntroduction());

        // 게시글 조회해서 넣어줘야됨.
        List<Map<String, String>> articleList = articleRepository.findArticleListByMemberId(memberId);

        data.put("currentPage", 0);
        data.put("totalPage", articleList.size());
        data.put("articleList", articleList);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage(true, "프로필 조회 성공", "", data),HttpStatus.OK);
    }
    @PutMapping("/profile/introduction/{introduction}")
    public ResponseEntity<ResponseMessage> editIntroduction(@PathVariable String introduction) {
        //log.info(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        int memberId = ((MemberAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMember().getId();
        Profile profile = memberService.editProfileIntroduction(memberId, introduction);
        Map<String, Object> data = new HashMap<>();
        data.put("introduction", profile.getIntroduction());
        return new ResponseEntity<>(new ResponseMessage(true, "소개글 수정 완료", "", data), HttpStatus.OK);
    }

    @PutMapping("/profile/nickname/{nickname}")
    public ResponseEntity<ResponseMessage> editNickname(@PathVariable String nickname) {
        //log.info(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        int memberId = ((MemberAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMember().getId();
        Profile profile = memberService.editProfileNickname(memberId, nickname);
        //log.info("영속성 : " + ((MemberAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMember().getNickname());
        Map<String, Object> data = new HashMap<>();
        data.put("nickname", profile.getMember().getNickname());
        return new ResponseEntity<>(new ResponseMessage(true, "닉네임 수정 완료", "", data), HttpStatus.OK);
    }

    @PutMapping("/profile/image")
    public ResponseEntity<ResponseMessage> editImage(@RequestParam MultipartFile multipartFile) {
        int memberId = ((MemberAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMember().getId();
        File file = memberService.editProfileImage(memberId, multipartFile);

        Map<String, Object> data = new HashMap<>();
        data.put("file", file);
        return new ResponseEntity<>(new ResponseMessage(true, "프로필 이미지 수정 완료","", data), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public UrlResource showImage(@PathVariable String filename) throws
            MalformedURLException {
        return new UrlResource("file:" + "\\upload\\" + filename);
    }
}
