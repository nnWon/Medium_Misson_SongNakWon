package com.ll.medium.controller;

import com.ll.medium.domain.Member;
import com.ll.medium.dto.MemberJoinDto;
import com.ll.medium.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm(MemberJoinDto memberJoinDto) {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(@Validated MemberJoinDto memberJoinDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return "joinForm";
        }

        if (memberJoinDto.isNotMatchPasswords()) {
            bindingResult.reject(memberJoinDto.getPasswordConfirm(),"비밀번호을 확인하세요.");
            return "joinForm";
        }

        if (memberService.checkDuplicateMember(memberJoinDto.getUsername())) {
            bindingResult.reject(memberJoinDto.getUsername(),"이미 존재하는 아이디입니다.");
            return "joinForm";
        }
        Member member = memberJoinDto.toEntity();
        memberService.join(member);

        return "redirect:/";
    }
}
