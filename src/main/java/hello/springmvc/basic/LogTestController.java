package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class LogTestController {

    // @Slf4j 덕분에 아래의 코드 라인은 작성하지 않아도 롬복이 작성해줄 수 있음.
    //private final Logger log = LoggerFactory.getLogger(getClass()); // LogTestController.class도 가능

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "test";
        System.out.println("name = " + name);

        // 아래로 갈수록 심각도가 높아지는것이다.
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info("info log={}", name);
        log.warn("warn log={}", name);
        log.error("error log={}", name); // {}이 치환되는거임 {} {} 하면 name, name2하면 된다.

        return "ok"; // @Controller라면 뷰 이름이 반환되어 뷰 리졸버 찾는 과정을 거치나, @RestController는 바디에 데이터를 그대로 넣는다.
    }
}
