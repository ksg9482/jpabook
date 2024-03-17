package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

//스프링에 인티그레이션 해서 하는 방식
//@RunWith(SpringRunner.class) //junit이랑 엮어서 스프링이랑 같이 실행하겠다는 뜻
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional//이게 있어야 롤백됨
class MemberServiceTest {

    //테스트 케이스니 다른게 참조할 일이 없음. 그래서 간단하게 처리
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Autowired EntityManager em;

    //이거 코드 스니펫 만들어두기. 라이브템플릿 tdd로 만들어두는게?
//    @Test
//    public void () throws Exception {
//        //given
//
//        //when
//
//        //then
//    }

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);
        //insert가 안나감. jpa는 persist해도 바로 삽입하지 않음. commit 때 insert로 넣는다. 그래서 jpa는 commit이 중요함.
        //그런데 spring의 @Transactional은 commit을 안하고 rollback을 해버림. 그래서 insert로 실제로 들어가지 않음.
        //롤백이지만 넣으면 들어가는거 보고 싶다면 테스트 케이스 메서드에 @Rollback(false) 쓰면 됨

        //then
        Assertions.assertThat(memberRepository.findOne(savedId)).isEqualTo(member);
//        assertEquals(member, memberRepository.findOne(savedId));

    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);

        //then
        //assertThrows를 이용해서 throw 잡아서 처리
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
        //try catch를 이용해서 throw 잡아서 테스트 처리
//        try {
//            memberService.join(member2);
//        } catch (IllegalStateException e) {
//            return;
//        }

        //fail 테스트를 실패시킴. 여기까지 오면 안되는데 와버렸다
//        fail("예외가 발생해야 한다");
    }


}