package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B") //지정 안하면 클래스명 들어감.
@Getter
@Setter
public class Book extends Item {
    private String author;

    private String isbn;
}
