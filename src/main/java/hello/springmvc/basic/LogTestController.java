package hello.springmvc.basic;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @Controller가 아닌 @RestController를 사용한 이유는
// @Controller의 경우 문자열을 return하면 view의 형태로 반환돼서 view resolver를 거치고~~~ 복잡한 과정이 생길 수 있는데
// @RestController는 문자열 그대로를 반환한다고 한다.
//@Slf4j
@RestController
public class LogTestController {

    // @Slf4j 애노테이션이 아래의 코드를 작성해준다.
    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);
        log.trace("trace log={}", name);
        // 중요!! log.trace("trace log is"+ name); 이라는 코드가 있을때 name을 붙일때 콤마 대신 +를 사용하게 되면,
        // 자바에서 이를 인식해서 연산을 수행한다. 하지만 로그 레벨에 따라서 trace는 표시가 될수도 있고, 안될수도 있는데, 연산이 이뤄지면
        // 메모리의 낭비이다. 따라서 +는 사용하면 안되고, 콤마를 사용하도록 한다.

        log.debug("debug log={}", name);
        log.info(" info log={}", name);
        log.warn("warn log={}", name);
        log.error("error log={}", name);


        return "ok";
    }
}
