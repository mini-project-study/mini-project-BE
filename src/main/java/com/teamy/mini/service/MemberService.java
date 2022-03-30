package com.teamy.mini.service;

import com.teamy.mini.domain.File;
import com.teamy.mini.domain.LoginInfo;
import com.teamy.mini.domain.Member;
import com.teamy.mini.domain.Profile;
import com.teamy.mini.error.customException.InquiryException;
import com.teamy.mini.repository.FileRepository;
import com.teamy.mini.repository.MemberRepository;
import com.teamy.mini.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final FileRepository fileRepository;

    public void registerProfile(Profile profile) {
        profileRepository.saveProfile(profile);
    }

    public void registerFile(File file) {
        fileRepository.saveFile(file);
    }

    // 회원 가입
    @Transactional
    public Member registerMember(Member member){
        // 멤버 등록
        memberRepository.saveMember(member);
        // 프로필 기본 이미지 조회
        File basicProfile = fileRepository.findFile(1);
        // 새로 가입한 멤버 프로필 등록
        Profile profile = new Profile(member, basicProfile, "");
        profileRepository.saveProfile(profile);
        return member;
    }

    @Transactional
    public Member login(LoginInfo loginInfo) throws InquiryException {
        return memberRepository.findByEmail(loginInfo.getEmail());
    }

    public Profile findProfile(int memberId) {
        return profileRepository.findProfile(memberId);
    }

    @Transactional
    public Profile editProfileIntroduction(int memberId, String introduction) {
        return profileRepository.updateProfileIntroduction(memberId, introduction);
    }

    @Transactional
    public Profile editProfileNickname(int memberId, String nickname) {
        memberRepository.updateNickname(memberId, nickname);
        return findProfile(memberId);
    }
}
