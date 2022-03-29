package com.teamy.mini.repository;

import com.teamy.mini.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Repository
public class MemberRepository {
    private final EntityManager em;

    // 회원 저장
    public void saveMember(Member member) {
        em.persist(member);
    }

    public Member findByEmail(String email) {
        log.info("email : {}", email);
        Member member = null;


        member = (Member) em.createQuery("SELECT M FROM Member M WHERE M.email = :email").setParameter("email", email).getSingleResult();
        log.info("findByEmail {} : ", member);


        return member;
    }

    public Member findById(int memberId){
        return em.find(Member.class, memberId);
    }
    public Member updateNickname(int memberId, String nickname) {

        em.createQuery("UPDATE Member M SET M.nickname = :nickname WHERE M.id = :memberId")
                .setParameter("nickname", nickname)
                .setParameter("memberId", memberId)
                .executeUpdate();

        return findById(memberId);
    }
}
