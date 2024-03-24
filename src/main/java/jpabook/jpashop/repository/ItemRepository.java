package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        //아이템은 처음 저장할 때 아이디가 없음
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);//merge는 업데이트 비슷함.
            //들어온 item객체는 영속성 컨텍스트에서 관리 안함. merge를 통해 반환된 객체가 관리되는 객체.
            //만약 이후 로직이 더 필요하면 관리되는 영속성 엔티티를 이용해야 한다.
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("SELECT i FROM Item i", Item.class)
                .getResultList();
    }

}
