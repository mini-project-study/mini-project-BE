package com.teamy.mini.service;

import com.teamy.mini.domain.LoginInfo;
import com.teamy.mini.domain.Member;
import com.teamy.mini.error.customException.InquiryException;
import com.teamy.mini.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 가입
    @Transactional
    public Member registerMember(Member member){
        memberRepository.saveMember(member);
        return member;
    }

    @Transactional
    public Member login(LoginInfo loginInfo) throws InquiryException {
        return memberRepository.findByEmail(loginInfo.getEmail());
    }

}
