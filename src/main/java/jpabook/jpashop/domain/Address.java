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

    //jpa는 객체를 생성할 때 프록시, 리플렉션등의 기능을 이용한다. 그 때 기본 생성자를 사용.
    //public을 쓰면 접근이 너무 열려있기 때문에 jpa는 pretected까지 허용해준다.
    //어차피 상속할일 없기 때문에 jpa 스펙용으로 한다.
    protected Address() {}
}
