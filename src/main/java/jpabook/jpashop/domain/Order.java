package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id") //id로 해도 되는 데 DBA가 order_id 방식을 선호함
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")//join하는 포인트 이름이 member_id
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne//1대1일 경우 FK는 많이 보는 쪽에. 여기가 연관관계 주인.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간 하이버네이트가 시간 넣어줌

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]
}
