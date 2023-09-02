package hello.springmvc;

import hello.springmvc.login.domain.member.Member;
import hello.springmvc.login.domain.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDatainit {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        Member member = new Member();
        member.setLoginId("admin");
        member.setPassword("1234");
        member.setName("테스트");
        memberRepository.save(member);
    }
}
