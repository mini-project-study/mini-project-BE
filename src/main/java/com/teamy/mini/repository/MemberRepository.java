package com.teamy.mini.repository;

import com.teamy.mini.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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
}
