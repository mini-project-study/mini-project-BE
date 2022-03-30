package com.teamy.mini.service;

import com.teamy.mini.domain.File;
import com.teamy.mini.domain.LoginInfo;
import com.teamy.mini.domain.Member;
import com.teamy.mini.domain.Profile;
import com.teamy.mini.error.customException.InquiryException;
import com.teamy.mini.repository.FileRepository;
import com.teamy.mini.repository.MemberRepository;
import com.teamy.mini.repository.ProfileRepository;
import com.teamy.mini.utill.FileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final FileRepository fileRepository;
    private final FileManager fileManager;

    public void registerProfile(Profile profile) {
        profileRepository.saveProfile(profile);
    }

    @Transactional
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

    @Transactional
    public File editProfileImage(int memberId, MultipartFile multipartFile) throws RuntimeException {

        Profile profile = findProfile(memberId);

        log.info("프로필 몇번인데 : " + profile.getFile().getId());
        // 기본 프로필 이미지가 아닐 경우 서버에서 이미지 삭제
        if(profile.getFile().getId() != 1) {
            if(fileManager.deleteFile(profile.getFile().getSystemFileName()) == false)
                // 파일 삭제 실패
                throw new RuntimeException("프로필 수정 : 삭제할 이미지가 존재하지 않음");
            else {
                // 파일 삭제 성공 시
                // 파일 디비에서도 삭제
                fileRepository.deleteFile(profile.getId());
            }

        }

        File file = fileManager.uploadFile(multipartFile);
        fileRepository.saveFile(file);
        //profile = profileRepository.updateProfileImage(memberId, file); // 여기서 profile 이 업데이트 될 줄 알았는데 업데이트 되지 않은 이전 정보를 불러옴..
        //return profile;
        return file;
    }
}
