package jpabook.jpashop.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원이름은 필수입니다") //필수로 지정.
    //에러 있으면 hasError 객체 필드에 값 들어감. 타임리프에서 이걸 감지하면 html에 뿌리는 개념.
    private String name;

    private String city;
    private String street;
    private String zipcode;

}
