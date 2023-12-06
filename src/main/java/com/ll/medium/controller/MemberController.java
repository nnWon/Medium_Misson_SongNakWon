package com.ll.medium.controller;

import com.ll.medium.domain.Member;
import com.ll.medium.dto.MemberJoinDto;
import com.ll.medium.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(MemberJoinDto memberJoinDto) {

        if (memberJoinDto.getPassword() != memberJoinDto.getPasswordConfirm()) {
            //Todo: 패스워드 불일치 오류 처리
        }

        if (memberService.checkDuplicateMember(memberJoinDto.getUsername())) {
            //Todo: 오류 처리
        }
        Member member = memberJoinDto.toEntity();
        memberService.join(member);

        return "redirect:/";
    }
}
