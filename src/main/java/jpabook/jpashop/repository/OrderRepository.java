package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    //동적쿼리 안쓸때. 이게 제일 간단. 하지만 동적쿼리가 들어가면 못쓴다.
    public List<Order> findAllBasic(OrderSearch orderSearch) {

        return em.createQuery("SELECT o FORM Order o JOIN o.member m" +
                        " WHERE o.status = :status" +
                        " AND m.name LIKE :name", Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(1000) //최대 1000건
                .getResultList();

    }

    public List<Order> findAllByString(OrderSearch orderSearch) {
        String jpql = "SELECT o FORM Order o JOIN o.member m";
        boolean isFirstCondition = true;

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            //status가 지정되면 status 쿼리 만듦
            if (isFirstCondition) { //처음이면 WHERE 붙여야 하고 아니면 AND로 이어야 한다.
                jpql += " WHERE";
                isFirstCondition = false;
            } else {
                jpql += " AND";
            }
            jpql += " o.status = :status";
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {//문자열 값 가지고 있는지 확인
            if (isFirstCondition) {
                jpql += " WHERE";
                isFirstCondition = false;
            } else {
                jpql += " AND";
            }
            jpql += " m.name = :name";
        }

        //쿼리가 동적이면 쿼리 바인딩도 동적으로 해야 한다. -> 해야하는 일이 정직하게 계속 늘어나게 됨
        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }

    //jpql을 JAVA코드로 작성할 수 있도록 JPA에서 표준으로 제공
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        //실무 입장에선 실용성 없음. 유지보수가 안됨. 어떤 쿼리가 나올지 안보임.
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Order> cq = cb.createQuery(Order.class);
            Root<Order> o = cq.from(Order.class);
            Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
            List<Predicate> criteria = new ArrayList<>();
            //주문 상태 검색
            if (orderSearch.getOrderStatus() != null) {
                Predicate status = cb.equal(o.get("status"),
                        orderSearch.getOrderStatus());
                criteria.add(status);
            }
            //회원 이름 검색
            if (StringUtils.hasText(orderSearch.getMemberName())) {
                Predicate name =
                        cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName()
                                + "%");
                criteria.add(name);
            }
            cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
            TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
            return query.getResultList();
    }
}
