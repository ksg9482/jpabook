package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor //repository도 쓸 수 있고, 일관성 유지 역할도 됨
//스프링에선 안되고, 스프링 부트가 표준 인젝션(EntityManager는 AutoWired로 안되고 @PersistenceContext가 있어야 한다) 방법을 안써도 AutoWired 되도록 해주는 것
//springDataJpa가 AutoWired되도록 지원.
public class MemberRepository {

//    @PersistenceContext//스프링이 만들어서 주입해줌
    private final EntityManager em;

//    @PersistenceUnit //직접 엔티티메니저를 주입하고 싶을 때.
//    private EntityManagerFactory emf;

    public void save(Member member) {
        em.persist(member); //저장
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);//엔티티에서 db메서드를 쓰는게 아니라 엔티티메니저에 엔티티를 주입해서 처리
    }

    //첫번째 찾는 JPQL(엔티티를 기준으로 엔티티 찾으라 함), 두번째 반환 타입
    public List<Member> findAll() {
        return em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
    }


    public List<Member> findByName(String name) {
        //파라미터 바인딩
        return em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class)
                .setParameter("name", name) //실제 바인딩 하는 곳
                .getResultList();
                
    }
}
