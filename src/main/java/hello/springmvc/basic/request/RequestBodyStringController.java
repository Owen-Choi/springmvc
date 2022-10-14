package hello.springmvc.basic.request;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    // v1 : request와 response를 객체로 두고 그 객체 안에서 inputStream -> messageBody 순으로 받아오는 방법
    @PostMapping("request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");
    }

    // v2 : 그냥 input, output stream만 넘기면 받아올 수 있음
    @PostMapping("request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

        responseWriter.write("ok");
    }

    // v3 : HttpEntity를 활용하면 input, output stream은 물론이고 request, response 객체를 받을 필요가 없다.
    @PostMapping("request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        String messageBody = httpEntity.getBody();
//        HttpHeaders headers = httpEntity.getHeaders();
        // 헤더 정보도 얻을 수 있음
        log.info("messageBody={}", messageBody);

        // HttpEntity를 사용하게 되면 view를 조회하지 않는다.
        return new HttpEntity<>("ok");
    }


    // v4 : @RequestBody 애노테이션을 통해 body를 바로 불러올 수 있다.
    // 반환은 여태 써온 @ResponseBody 애노테이션으로 응답의 body에 데이터를 넣어줄 수 있다.
    @ResponseBody
    @PostMapping("request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {
        log.info("messageBody={}", messageBody);
        return "ok";
    }
}
