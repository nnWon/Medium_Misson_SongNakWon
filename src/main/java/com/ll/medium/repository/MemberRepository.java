package com.ll.medium.repository;

import com.ll.medium.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);

    //Todo:fetch조인으로 바꿀것
    Optional<Member> findByUsername(String username); //같은 username을 가진 Member가 없을 수 있으니 반환타입 Optional로 설정
}
