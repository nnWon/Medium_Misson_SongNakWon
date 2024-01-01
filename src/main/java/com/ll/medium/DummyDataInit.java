package com.ll.medium;

import com.ll.medium.domain.Member;
import com.ll.medium.domain.Post;
import com.ll.medium.service.MemberService;
import com.ll.medium.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class DummyDataInit {

    private final MemberService memberService;
    private final PostService postService;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {

        Member user1 = new Member("user1", "1234", "nana@naver.com");
        Member user2 = new Member("user2", "1234", "nana2@naver.com");
        Member user3 = new Member("user3", "1234", "nana3@naver.com");

        memberService.join(user1);
        memberService.join(user2);
        memberService.join(user3);


        int dummyDataSize = 10;
        Member[] membershipMembers = new Member[dummyDataSize];

        // 유료멤버십 회원 100개 생성
        for (int i = 0; i < dummyDataSize; i++) {
            Member membershipMember = new Member("membership" + (i + 1), "1234", "membership@gmail.com");
            membershipMember.convertToMembershipMember();
            memberService.join(membershipMember);
            membershipMembers[i] = membershipMember;
        }

        Random random = new Random();
        for (int i = 0; i < dummyDataSize; i++) {
            int randomIndex = random.nextInt(dummyDataSize);
            Member randomMembershipMember = membershipMembers[randomIndex];

            Post post = new Post("Title" + (i + 1), "body" + (i + 1), random.nextBoolean(), random.nextBoolean());
            post.addMember(randomMembershipMember);
            postService.savePost(post);
        }


    }
}
