package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
            //FK를 매핑한다
    )//다대다 테이블은 컬렉션처럼 있는게 아니라서 중간 테이블로 풀어줘야 한다
    //풀지않고 다대다로 해도 테이블을 만들어주지만 조인테이블에 다른 필드를 추가하거나 하는 일은 못함
    //그래서 아예 중간테이블을 별로도 만들고 일대다, 다대일로 풀어내는 것
    private List<Item> items = new ArrayList<>();

    //내 부모는 곧 내 타입(같은 테이블을 쓴다)
    @ManyToOne
    @JoinColumn(name = "parent_id") //한 부모는 여러 자식 카테고리를 갖는다
    private Category parent;

    @OneToMany(mappedBy = "parent")//자식은 한 부모 카테고리를 갖음
    private List<Category> child;  //같은 테이블안에서 관계를 맺음
}
