package com.teamy.mini.repository;

import com.teamy.mini.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtTestRepository extends JpaRepository<Member, Integer> {

    //public Member findByUsername(String email);
    public Member findByEmail(String email);
}
