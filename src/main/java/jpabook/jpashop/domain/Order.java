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

    private List<OrderItem> orderItems = new ArrayList<>();

    private Delivery delivery;

    private LocalDateTime orderDate; //하이버네이트가 시간 넣어줌

    private OrderStatus status; //주문상태 [ORDER, CANCEL]
}
