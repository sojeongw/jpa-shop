package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // 주문 당시의 가격. 때마다 바뀔 수 있다.
    private int orderPrice;

    private int count;

    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        // 쿠폰 등의 가격 변경 가능성 때문에 객체를 따로 만든다.
        OrderItem orderItem = new OrderItem();

        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        // 넘어온 것만큼 재고를 뺀다.
        item.removeStock(count);

        return orderItem;
    }
}
