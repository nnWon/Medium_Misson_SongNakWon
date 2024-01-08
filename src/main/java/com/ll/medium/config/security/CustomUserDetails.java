package com.ll.medium.config.security;

import com.ll.medium.domain.Member;
import com.ll.medium.domain.MemberRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CustomUserDetails extends User {

    private Member member;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public CustomUserDetails(Member member) {
        super(member.getUsername(), member.getPassword(), createAuthorities(member));
        this.member = member;
    }

    private static Collection<? extends GrantedAuthority> createAuthorities(Member member) {
        List<GrantedAuthority> authorities = member.getRoleSet().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        if (member.isPaid()){
            authorities.add(new SimpleGrantedAuthority(MemberRole.ROLE_PAID.name()));
        }
        return authorities;
    }
}
