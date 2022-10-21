package hello.springmvc.itemservice.domain.item;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data
//@Data는 생성/수정에 관한 모든걸 포함하므로 핵심 domain에서 사용하는 것은 위험하다고 한다.
@Getter @Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

}
