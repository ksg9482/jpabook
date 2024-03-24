package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    //상품 서비스는 사실상 상품 리포지토리에 위임하는 클래스. 단순 위임을 서비스까지 만들어야 하는가 고민해 볼 필요도 있다.

    @Transactional //root Transactional의 readOnly 대응
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        //findItem으로 찾아온 객체는 영속상태. 변경감지가 일어난다
        //필요한 데이터만 받아서 변경감지로 업데이트 한다. 데이터가 너무 많으면 서비스에 dto를 만들어서 필요한 데이터를 전달한다.
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);

        //save등 호출하지 않아도 변경감지를 통해 업데이트 커밋 시점에 업데이트 쿼리 생성됨.
        //Transactional이 있으므로 플러시를 날린다. 플러시 -> 영속성 컨텍스트에 있는 엔티티 중 변경된 것이 무엇인지 찾아서 업데이트 한다.


    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
