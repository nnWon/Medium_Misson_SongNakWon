package com.ll.medium.controller;

import com.ll.medium.domain.Member;
import com.ll.medium.dto.MemberJoinFormDto;
import com.ll.medium.dto.MemberLoginFormDto;
import com.ll.medium.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm(MemberJoinFormDto memberJoinFormDto) {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(@Validated MemberJoinFormDto memberJoinFormDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "joinForm";
        }

        if (memberJoinFormDto.isNotMatchPasswords()) {
            //Todo:오류메시지 한 곳에 모아서 정리하기
            bindingResult.reject(memberJoinFormDto.getPasswordConfirm(), "비밀번호을 확인하세요.");
            return "joinForm";
        }

        if (memberService.checkDuplicateMember(memberJoinFormDto.getUsername())) {
            bindingResult.reject(memberJoinFormDto.getUsername(), "이미 존재하는 아이디입니다.");
            return "joinForm";
        }
        Member member = memberJoinFormDto.toEntity();
        memberService.join(member);

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String loginForm(MemberLoginFormDto memberLoginFormDto) {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(MemberLoginFormDto memberLoginFormDto) {
        log.info("memberLoginDto={}", memberLoginFormDto);
        return "loginForm";
    }
}
