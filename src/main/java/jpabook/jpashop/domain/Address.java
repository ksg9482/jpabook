package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

//내장타입이라는 의미 - 어딘가에 내장 할 수 있다
@Embeddable //하나만 해도 되지만 보통 둘 다 해준다.
@Getter @Setter
public class Address {

    private String city;

    private String street;

    private String zipcode;
}
