package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
  private final ItemRepository itemRepository;

  // readOnly가 아닌 곳에 안 달아주면 저장이 안된다.
  @Transactional
  public void saveItem(Item item) {
    itemRepository.save(item);
  }

  public List<Item> findItem() {
    return itemRepository.findAll();
  }

  public Item findOne(Long itemId) {
    return itemRepository.findOne(itemId);
  }

  public List<Item> findItems() {
    return itemRepository.findAll();
  }

  @Transactional
  public void updateItem(Long itemId, Item param) {
    Item findItem = itemRepository.findOne(itemId);
    findItem.setPrice(param.getPrice());
    findItem.setName(param.getName());
  }

  /**
   * 영속성 컨텍스트가 자동 변경
   */
  @Transactional
  public void updateItem(Long id, String name, int price) {
    Item item = itemRepository.findOne(id);
    item.setName(name);
    item.setPrice(price);
  }
}
