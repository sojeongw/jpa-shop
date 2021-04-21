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
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders") // DB에 order by가 예약어로 걸려있어서 테이블명을 따로 지정해준다.
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
}
