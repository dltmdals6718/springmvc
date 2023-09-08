package hello.springmvc.login.web.filter;

import hello.springmvc.login.web.session.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;


@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = {"/", "/members/add", "/login", "/logout", "/css/*"};
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try{
            log.info("인증 체크 필터 시작 {}", requestURI);

            if(isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if(session==null || session.getAttribute(SessionConst.LOGIN_MEMBER)==null) {
                    log.info("미인증 사용자 요청 {}", requestURI);

                    //로그인으로 Redirect
                    httpResponse.sendRedirect("/login?redirectURL="+requestURI);
                    return; // return하더라도 try catch 안에 finally는 실행된다??
                }
            }

            chain.doFilter(request, response);

        } catch(Exception e) {
            throw e; // 톰캣까지 예외를 보내주자.
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }

    }

    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
