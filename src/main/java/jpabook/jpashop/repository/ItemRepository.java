package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
  private final EntityManager em;

  public void save(Item item){
    if (item.getId() == null) {
      // 없으면 신규로 등록한다.
      em.persist(item);
    } else {
      // 업데이트와 비슷하다고 일단 알아두고 나중에 설명한다.
      em.merge(item);
    }
  }

  public Item findOne(Long id) {
    return em.find(Item.class, id);
  }

  public List<Item> findAll() {
    return em.createQuery("select i from Item i", Item.class)
        .getResultList();
  }

  @Transactional
  public void update(Item item) {
    Item mergedItem = em.merge(item);
  }

}
