package com.teamy.mini.security;

import com.teamy.mini.domain.Member;
import com.teamy.mini.repository.JwtTestRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@NoArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private JwtTestRepository testRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("----- PrincipalDetailService > loadUserByUsername 실행");
        Member member = testRepository.findByEmail(username); //jpa 사용해서 findByUsername -> findByEmail로 사용

        if(member == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        return new PrincipalDetails(member);
    }
}
