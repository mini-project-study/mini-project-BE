package com.teamy.mini.jwt;

import com.teamy.mini.domain.JwtToken;
import com.teamy.mini.domain.Member;
import com.teamy.mini.domain.ResponseMessage;
import com.teamy.mini.error.ErrorCode;
import com.teamy.mini.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
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
        Member member = memberRepository.findByEmail(username);


        //id 없을 때
        if(member == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        log.info(" member : " + member.getEmail() + " " + member.getNickname() + " " + member.getPassword());

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("USER"));

        return new User(member.getEmail(), member.getPassword(), roles);
    }
}
