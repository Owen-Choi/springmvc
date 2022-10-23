package hello.springmvc.itemservice.web.basic;

import hello.springmvc.itemservice.domain.item.Item;
import hello.springmvc.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {

        itemRepository.save(item);
//        model.addAttribute("item", item);
        // @ModelAttribute를 사용하면 위 코드는 자동으로 실행시켜준다고 한다.
        // 여기서 addAttribute의 이름은 "item"으로 들어가는데, 이는 매개변수에서 직접 지정한 이름이다.
        // 만약 @ModelAttribute("item2")라고 지정을 했다면 오류가 발생한다.
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        // @ModelAttribute에서 이름을 지정하지 않는다면 Class 네임의 앞글자만 소문자로 바꾼 값을 default로 사용한다.
        // 만약 클래스 이름이 HelloData라면, 이름을 자동으로 helloData로 지정한다.
        itemRepository.save(item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item) {
        // ModelAttribute는 생략 가능하다.
        itemRepository.save(item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }
    
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";

        // return "redirect:/basic/items/{itemId}";의 {itemId}는 redirectAttributes에서 꺼내온 값이고,
        // return문에서 사용되지 않은 status의 경우에는 쿼리 파라미터 ? 로 붙어서 사용된다고 한다.
        // 이렇게 RedirectAttributes를 사용하는 이유는 사용자 친화적인 화면 구성(~~~아이템이 수정되었습니다)과 관련된 요구사항이다.
        // 저장이 잘 되었으면 상품 상세 화면에 "저장되었습니다"와 같은 메세지를 보여달라는 요구사항이 있을 수 있기 때문이다.
        // 후에 html에 <h2 th:if="${param.status}" th:text="'저장 완료'"></h2> 같은 코드를 추가하여 처리하면 된다.
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        // 이렇게 리다이렉트로 하면 아래의 링크로 url이 바뀌면서 컨트롤러부터 다시 호출한다고 한다.
       return "redirect:/basic/items/{itemId}";
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
