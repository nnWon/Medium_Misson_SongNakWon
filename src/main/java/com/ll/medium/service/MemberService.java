package com.ll.medium.service;

import com.ll.medium.domain.Member;
import com.ll.medium.domain.UserRole;
import com.ll.medium.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean checkDuplicateMember(String username) {
        return memberRepository.existsByUsername(username);
    }

    public Member join(Member member) {
        String encodedPassword = bCryptPasswordEncoder.encode(member.getPassword());
        member.changeToEncodedPassword(encodedPassword);
        member.addRole(UserRole.ROLE_USER);
        return memberRepository.save(member);
    }
}
