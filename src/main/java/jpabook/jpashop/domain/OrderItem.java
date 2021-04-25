package jpabook.jpashop.domain;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

  @Id
  @GeneratedValue
  @Column(name = "order_item_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "item_id")
  private Item item;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  private int orderPrice;

  private int count;

  // 생성 메서드
  public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
    // 쿠폰 등의 가격 변경 가능성 때문에 따로 만든다.
    OrderItem orderItem = new OrderItem();

    orderItem.setItem(item);
    orderItem.setOrderPrice(orderPrice);
    orderItem.setCount(count);

    // 넘어온 것만큼 재고를 뺀다.
    item.removeStock(count);

    return orderItem;
  }

  // 비즈니스 로직
  public void cancel() {
    // 해당 아이템에 대한 주문 수량을 취소한 만큼 원복시켜야 한다.
    getItem().addStock(count);
  }

  // 주문 가격과 수량이 orderItem에 있으므로 여기서 가격을 계산한다.
  public int getTotalPrice() {
    return getOrderPrice() * getCount();
  }
}
