package com.teamy.mini.security;

import com.teamy.mini.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MemberAccount extends User {

    private Member member;

    public MemberAccount(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getEmail(), member.getPassword(), authorities);
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

}
