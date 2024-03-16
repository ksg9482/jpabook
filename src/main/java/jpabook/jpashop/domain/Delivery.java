package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    Long id;

    //1대1 맵핑
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded //내장 타입
    private Address address;

    //enum주의!! enumated 넣어야 하고 enumtype넣을때 ORDINAL은 숫자로 들어간다. -> 중간에 다른 상태가 생기면 곤란. READY가 0이고 COMP가 1일 때 중간에 SET등 중간과정이면 이게 1을 써야함. 기존 COMP로 지정된 1과는 다르지만 DB는 어떻게 못해줌
    //STRING을 써야함.
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY(배송준비), COMP(배송완료)

}
