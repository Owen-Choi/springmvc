package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    // @RequestParam을 이용하지 않은 방법. v1
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        // 여기서 클래스 상단의 애노테이션이 @RestController가 아니라 @Controller기때문에 문자열을 반환하면
        // viewResolver가 view를 찾게된다. 이것을 방지하기 위해 @ResponseBody를 사용하는데,
        // 해당 애노테이션을 이용하면 viewResolver를 호출하지 않고 바로 body에 문자열을 집어넣게 된다.
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username, @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        // 여기서 클래스 상단의 애노테이션이 @RestController가 아니라 @Controller기때문에 문자열을 반환하면
        // viewResolver가 view를 찾게된다. 이것을 방지하기 위해 @ResponseBody를 사용하는데,
        // 해당 애노테이션을 이용하면 viewResolver를 호출하지 않고 바로 body에 문자열을 집어넣게 된다.
        return "ok";
    }

    // 변수명이 요청 파라미터 명과 일치한다면, 또한 자료형이 String ,int, Integer와 같은 단순 타입이라면
    // @RequestParam 애노테이션도 생략할 수 있다.
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        // 여기서 클래스 상단의 애노테이션이 @RestController가 아니라 @Controller기때문에 문자열을 반환하면
        // viewResolver가 view를 찾게된다. 이것을 방지하기 위해 @ResponseBody를 사용하는데,
        // 해당 애노테이션을 이용하면 viewResolver를 호출하지 않고 바로 body에 문자열을 집어넣게 된다.
        return "ok";
    }

    // required=false인 age에는 null이 들어갈 수도 있는데, int일 경우 response 500 에러가 난다.
    // 따라서 Integer로 선언해줘야 한다.
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    // 값이 없을때 기본값을 설정하고 싶으면 defaultValue를 활용한다.
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age) {

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String ,Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    // parameter로 넘어온 데이터를 객체에 저장하는 정석적인 방법.
    // 직접 setter로 넣어줘야해서 귀찮다.
//    @ResponseBody
//    @RequestMapping("/model-attribute-v1")
//    public String modelAttributeV1(@RequestParam String username, @RequestParam int age) {
//        HelloData helloData = new HelloData();
//        helloData.setUsername(username);
//        helloData.setAge(age);
//
//        return "ok";
//    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }


    /**
     * @ModelAttribute 생략 가능
     * String, int와 같은 단순 타입 = @RequestParam
     * 그 외 타입 (argument resolver로 지정한 타입 이외) = @ModelAttribute
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }


}
