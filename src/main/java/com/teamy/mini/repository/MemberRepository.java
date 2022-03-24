package com.teamy.mini.repository;

import com.teamy.mini.domain.Member;
import com.teamy.mini.error.customException.InquiryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Slf4j
@Repository
public class MemberRepository {
    private EntityManager em;

    @Autowired
    public MemberRepository(EntityManager em) {
        this.em = em;
    }

    // 회원 저장
    public void saveMember(Member member) {
        em.persist(member);
    }

    public Member findByEmail(String email) {
        log.info("email : {}", email);
        Member member = null;

        try{
            member = (Member) em.createQuery("SELECT M FROM Member M WHERE M.email = :email").setParameter("email", email).getSingleResult();
        } catch (Exception e) {
            throw new InquiryException("일치하는 로그인 정보가 없음");
        }

        return member;
    }
}
