package hello.springmvc.itemservice.web.basic;

import hello.springmvc.itemservice.domain.item.Item;
import hello.springmvc.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

//    @Autowired
//    final 전역변수가 하나라면 @Autowired는 생략 가능하다
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }
//    @RequiredArgsConstructor를 사용하면 하나만 존재하는 final 전역변수의 의존관계를 자동으로 주입할 수 있다.

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    //<form action="item.html" th:action="/basic/items/add" method="post">
    // 기존 item.html을 렌더링하는 것에 th:action="/basic/items/add" 코드를 이용해서 치환하였다.
    // 중요한 점은 th:action 뒤의 URL과 item.html이 렌더링되는 URL이 같다는 것인데, 메서드만 Get에서 Post로 달라진다.
    // 그 점을 이용해서 이렇게 addForm과 url은 같지만 다른 기능을 하는 save 함수를 만들 수 있다.
    // 참고로 <form action="item.html" th:action method="post">와 같이 action뒤의 url을 비워둬도 된다.

    @PostMapping("/add")
    public String save() {
        return "basic/addForm";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
