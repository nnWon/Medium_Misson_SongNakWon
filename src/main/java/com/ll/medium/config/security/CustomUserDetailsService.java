package com.ll.medium.config.security;

import com.ll.medium.domain.Member;
import com.ll.medium.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Todo:해당 유저가 없으면 Exception 던지기
        Member member = memberRepository.findByUsername(username) //패스워드는 스프링 시큐리티가 자동으로 처리한다.
                .orElseThrow();

        return new CustomUserDetails(member);
    }

}
