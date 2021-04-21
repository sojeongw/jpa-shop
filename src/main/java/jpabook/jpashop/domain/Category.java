package jpabook.jpashop.domain;

import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Category {

  @Id @GeneratedValue
  @Column(name = "category_id")
  private Long id;

  private String name;

  // 실무에서는 중간 테이블에 추가적인 필드를 넣을 수가 없어서 많이 쓰지 않는다.
  @ManyToMany
  // 중간 테이블에 매핑해주어야 한다.
  @JoinTable(name = "category_item",  // 중간 테이블 이름
      joinColumns = @JoinColumn(name = "category_id"),  // 중간 테이블에 FK로 있는 id
      inverseJoinColumns = @JoinColumn(name = "item_id")) // 반대편(item)에서 가져올 FK
  private List<Item> items = new ArrayList<>();

  // 카테고리는 계층 구조이므로 부모와 자식을 만들어준다.
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "parent_id")
  private Category parent;

  // parent를 기준으로 매핑된다.
  // 셀프로 양방향 연관 관계를 맺는 방식이다.
  @OneToMany(mappedBy = "parent")
  private List<Category> child = new ArrayList<>();

  // 연관 관계 편의 메서드
  public void addChildCategory(Category child) {
    this.child.add(child);
    child.setParent(this);
  }
}
