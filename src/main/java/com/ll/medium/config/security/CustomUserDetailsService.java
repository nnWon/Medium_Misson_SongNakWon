package com.ll.medium.config.security;

import com.ll.medium.domain.Member;
import com.ll.medium.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

        return createUser(member);
    }

    private User createUser(Member member) {
        return new User(member.getUsername(), member.getPassword(), createAuthorities(member));
    }

    private static List<GrantedAuthority> createAuthorities(Member member) {
        return member.getRoleSet().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }
}
