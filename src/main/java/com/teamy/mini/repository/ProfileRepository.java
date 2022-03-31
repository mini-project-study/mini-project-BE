package com.teamy.mini.repository;

import com.teamy.mini.domain.File;
import com.teamy.mini.domain.Profile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.net.MalformedURLException;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ProfileRepository {
    private final EntityManager em;

    // 프로필 작성
    public void saveProfile(Profile profile) {
        em.persist(profile);
    }


    public Profile findProfile(int memberId) {
        return (Profile) em.createQuery("SELECT P FROM Profile P WHERE P.member.id = :memberId")
                .setParameter("memberId", memberId)
                .getSingleResult();
    }

    public Profile updateProfileIntroduction(int memberId, String introduction) {

        em.createQuery("UPDATE Profile P SET P.introduction = :introduction WHERE P.member.id = :memberId")
                .setParameter("introduction", introduction)
                .setParameter("memberId", memberId)
                .executeUpdate();

        return findProfile(memberId);
    }

    public Profile updateProfileImage(int memberId, File file) {

        em.createQuery("UPDATE Profile P SET P.file = :file WHERE P.member.id = :memberId")
                .setParameter("file", file)
                .setParameter("memberId", memberId)
                .executeUpdate();

        return findProfile(memberId);
    }

}
