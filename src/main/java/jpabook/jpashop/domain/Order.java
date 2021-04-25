package jpabook.jpashop.domain;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
// DB에 order by가 예약어로 걸려있어서 테이블명을 따로 지정해준다.
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

  @Id
  @GeneratedValue
  @Column(name = "order_id")
  private Long id;

  // 누가 주문했는지 알기 위한 용도
  // 회원에게 주문은 여러 개지만, 해당 주문을 갖고있는 회원은 한 명이다.
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "order", cascade = ALL)
  private List<OrderItem> orderItems = new ArrayList<>();

  @OneToOne(fetch = LAZY, cascade = ALL)
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;

  private LocalDateTime orderDate;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  // 연관 관계 편의 메서드
  public void setMember(Member member) {
    this.member = member;
    member.getOrders().add(this);
  }

  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
    orderItem.setOrder(this);
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
    delivery.setOrder(this);
  }

  // 생성 메서드
  public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
    Order order = new Order();
    order.setMember(member);
    order.setDelivery(delivery);

    for (OrderItem orderItem : orderItems) {
      order.addOrderItem(orderItem);
    }

    order.setStatus(OrderStatus.ORDER);
    order.setOrderDate(LocalDateTime.now());

    return order;
  }

  // 비즈니스 로직
  /**
   * 주문 취소
   */
  public void cancel() {
    if(delivery.getStatus() == DeliveryStatus.COMP) {
      throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
    }

    this.setStatus(OrderStatus.CANCEL);

    // IDE 에디터 색때문에 정말 this를 꼭 명시적으로 보여줘야할 때가 아니면
    // this.orderItems보다 orderItems를 선호한다.
    for (OrderItem orderItem : orderItems) {
      // 여러 상품을 주문했을 때 다 주문 취소를 해줘야 한다.
      orderItem.cancel();
    }
  }

  // 조회 로직
  /**
   * 전체 주문 가격 조회
   */
  public int getTotalPrice() {
    return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
  }
}
