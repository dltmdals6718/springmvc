package hello.springmvc.login.web;

import hello.springmvc.login.domain.member.Member;
import hello.springmvc.login.domain.member.MemberRepository;
import hello.springmvc.login.web.session.SessionConst;
import hello.springmvc.login.web.session.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
//@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    //@GetMapping("/")
    public String home() {
        return "home";
    }

    //@GetMapping("/")
    public String homeLogin(@CookieValue(value = "memberId", required = false) Long memberId, Model model) {
        if(memberId==null) {
            return "home";
        } else {
            Member loginMember = memberRepository.findById(memberId);
            if(loginMember==null) {
                return "home";
            }

            model.addAttribute("member", loginMember);
            return "loginHome";

        }
    }

    //@GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        //세션 관리자에 저장된 회원 정보 조회
        Member member = (Member)sessionManager.getSession(request);
        if(member==null)
            return "home";

        model.addAttribute("member", member);
        return "loginHome";

    }

    //@GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        if(session==null)
            return "home";

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(loginMember==null)
            return "home";

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping("/")
    public String homeLoginV4(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        if(loginMember==null)
            return "home";

        model.addAttribute("member", loginMember);
        return "loginHome";

    }

    //@PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("memberId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}