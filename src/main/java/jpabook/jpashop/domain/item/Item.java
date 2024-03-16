package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//추상 클래스 -> 이미 설계에서 구현체가 여러개.
@Entity
//상속관계 전략을 잡아야 한다.
//JOINED: 가장 정규화된 스타일. SINGLE_TABLE: 한테이블에 전부다(싱글테이블 전략) TABLE_PER_CLASS: 각각 하나씩 매핑,
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")//컬럼을 뭘로 구분할지
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
