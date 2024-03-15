package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne//하나의 아이템은 여러 오더 아이템을 가질 수 있다.(같은 아이템이 여러 주문에 들어갈 수 있음)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order; //하나의 오더가 여러 오더아이템을 가질 수 있다

    private int orderPrice; //주문당시의 가격. 가격이 바뀔 수 있다.

    private int count; //주문 당시 수량.
}
