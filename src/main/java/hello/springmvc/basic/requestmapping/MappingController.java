package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    // 위처럼 url 여러개 지정 가능
    //@RequestMapping({"/hello-basic", "/hello-go"})

    // http method 지정 가능, 지정하지 않으면 모든 Method에 대해서 허용
    @RequestMapping(value = "/hello-basic", method = RequestMethod.GET)
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }


    // 아래와 같이 사용할 Method를 앞글자로 하는 축약 애노테이션도 사용 가능하다.
    /**
     * 편리한 축약 애노테이션 (코드보기)
     * @GetMapping
     * @PostMapping
     * @PutMapping
     * @DeleteMapping
     * @PatchMapping
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }


    /**
     * PathVariable 사용
     * 변수명이 같으면 생략 가능
     * @PathVariable("userId") String userId -> @PathVariable userId
     */
    // URL에서 붙어서 들어온 데이터를 빼서 사용하기 위해 PathVariable 애노테이션을 사용한다.
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        log.info("mappingPath userId={}", data);
        return "ok";
    }

    // 아래와 같이 PathVariable에 문자열을 건네주지 않고 userId로 변수명을 일치시켜도 동일하게 사용 가능하다.
//    @GetMapping("/mapping/{userId}")
//    public String mappingPath(@PathVariable String userId) {
//        log.info("mappingPath userId={}", userId);
//        return "ok";
//    }

    // 아래처럼 다중으로 PathVariable을 사용할 수도 있다.
    @GetMapping("/mapping/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    // consume은 미디어 타입 조건 매핑에 사용된다.
    // 들어온 요청의 헤더 중 Content-Type 필드의 값이 "application/json"이어야 응답을 보낸다.
    // consume : content-type을 의미, 서버입장에서 보면 요청의 content type을 소비하는 것이기 때문에 consume이라고 한다고 함.
//    @PostMapping(value="/mapping-consume", consumes = "application/json")
    @PostMapping(value="/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }


    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    // produce는 클라이언트가 "나는 content 타입이 text/html인걸 받아들일 수 있어!" 라는 의미라고 한다.
    // 보내는 요청의 헤더 중 Accept 필드가 "text/html"이어야만 응답을 보낸다.
//    @PostMapping(value = "/mapping-produce", produces = "text/html")
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }

}
