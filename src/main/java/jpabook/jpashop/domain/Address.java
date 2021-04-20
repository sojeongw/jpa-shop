package jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

// 내장 타입 정의
@Embeddable
@Getter
public class Address {

  @Column(length = 10)
  private String city;
  @Column(length = 20)
  private String street;
  @Column(length = 5)
  private String zipcode;

  public Address(String city, String street, String zipcode) {
    this.city = city;
    this.street = street;
    this.zipcode = zipcode;
  }

  // JPA가 생성 시에 리플렉션이나 프록시 같은 기술을 써야하는데
  // 기본 생성자가 없으면 사용할 수가 없어 만들어줘야 한다.
  // JPA 스펙 상 엔티티나 임베디드 타입은 기본 생성자를 public이나 protected로 설정해야 한다.
  // public보다는 안전하게 protected로 해주자.
  protected Address() {

  }
}
