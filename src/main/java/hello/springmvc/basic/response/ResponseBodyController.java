package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//@Controller
//@ResponseBody
// 하단의 코드를 보면 @ResponseBody 애노테이션이 적용된 것도 있고 적용되지 않은 것도 있다
// 그럴 경우 이렇게 클래스 레벨에서 @ResponseBody 애노테이션을 달아주면 아래에 모두 적용이 된다.
// 그리고 이렇게 @Controller와 @ResponseBody를 합친 것이 @RestController 이다.
@RestController
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    // 여기까지는 String 타입의 response body
    ///////////////////////////////////////////////////////////////////
    // 여기서부터는 JSON 타입의 response body

    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }


    // 이렇게 @ResponseBody를 활용해서 객체를 바로 반환하는 경우는 HttpStatus를 지정할 수 없다.
    // 이 경우 @ResponseStatus를 활용해서 HttpStatus를 지정하도록 한다.
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return helloData;
    }



}
