package com.teamy.mini.security;

import com.teamy.mini.domain.Member;
import com.teamy.mini.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = null;
        try {
            member = memberRepository.findByEmail(username);
        } catch (Exception e) {
            //log.info("Exception : {}", e);
            log.info("catch 왔음");
            throw new UsernameNotFoundException("일치하는 사용자 정보 없음");
        }

        log.info("사용자 정보 있음");
        log.info(" member : {}, {}, {}", member.getEmail(), member.getNickname(), member.getPassword());

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("USER"));

        return new MemberAccount(member, roles);

    }
}
