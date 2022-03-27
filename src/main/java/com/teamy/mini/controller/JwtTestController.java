package com.teamy.mini.controller;

import com.teamy.mini.domain.Member;
import com.teamy.mini.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class JwtTestController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MemberRepository memberRepository;
    
    //jwt 테스트를 위한 회원가입 메소드 (임시)
    @PostMapping("join")
    public void join(@RequestBody Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        memberRepository.saveMember(member);
        log.info("----- (임시) 회원가입 완");
    }
}
