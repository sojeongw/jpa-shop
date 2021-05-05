package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  @NotEmpty
  private String name;

  // 내장 타입을 사용할 때 붙인다.
  // Embedded, Embeddable 둘 중 하나만 있으면 되지만 명시적으로 둘 다 적는다.
  @Embedded
  private Address address;

  @JsonIgnore
  // order 테이블에 있는 member 필드에 의해 매핑된다는 것을 알려준다.
  // 읽기 전용이기 때문에 값을 수정해도 변경되지 않는다.
  @OneToMany(mappedBy = "member")
  private List<Order> orders = new ArrayList<>();
}
