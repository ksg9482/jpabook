package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id") //id로 해도 되는 데 DBA가 order_id 방식을 선호함
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")//join하는 포인트 이름이 member_id
    private Member member;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = ALL)//1대1일 경우 FK는 많이 보는 쪽에. 여기가 연관관계 주인.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간 하이버네이트가 시간 넣어줌

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]


    //==연관관계 (편의) 메서드==//
    //양방향 연관관계면 어느 한 엔티티에 값 넣을때 나머지 한쪽에도 넣어줘야 한다.
    //이걸 각각 하면 실수할수 있기 때문에 원자적으로 묶어 한번에 처리되도록 한다.
    //주로 컨트롤 하는 쪽에 위치하는 편이 좋다
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);//member에 있는 order컬렉션에 넣어준다.
    }

    public void setOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem); //orderItem컬렉션에 입력
        orderItem.setOrder(this); //orderItem 엔티티에 있는 order에 지금있는 order를 넣는다.
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
