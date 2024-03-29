package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //protected 생성자와 동일.
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)//하나의 아이템은 여러 오더 아이템을 가질 수 있다.(같은 아이템이 여러 주문에 들어갈 수 있음)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order; //하나의 오더가 여러 오더아이템을 가질 수 있다

    private int orderPrice; //주문당시의 가격. 가격이 바뀔 수 있다.

    private int count; //주문 당시 수량.

    //JPA는 protected까지 기본생성자를 지원함. protected를 통해 new를 통한 생성을 방지
//    protected OrderItem() {
//
//    }
    //생성이 단순하지 않음
    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);

        //가격 -> item에 가격이 있지만 쿠폰 받거나 할인 받거나 등 변수가 많음. 그래서 처리 끝난 결과물을 주입하는 것.
        orderItem.setCount(count);

        //재고도 줄어들어야 한다
        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
        //주문 수량만큼 다시 늘린다. 재고수량 원복
        getItem().addStock(count);
    }

    /**
     * 주문 상품 가격 전체 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

}
