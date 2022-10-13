package hello.springmvc.basic.requestmapping;


import org.springframework.web.bind.annotation.*;

@RestController
// 아래의 Mapping들은 모두 /mapping/users를 포함하기 때문에 클래스 단위에서 한번에 묶는다.
@RequestMapping("/mapping/users")
public class MappingClassController {

    @GetMapping
    public String users() {
        return "get users";
    }

    @PostMapping
    public String addUser() {
        return "post user";
    }


    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId) {
        return "get userId=" + userId;
    }

    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId) {
        return "update userId=" + userId;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return "delete userId=" + userId;
    }
}
