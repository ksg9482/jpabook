package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)//readOnly = true 조회 작업 최적화 함. 읽기는 readOnly 쓸것. root에 하면 기본 적용
//@AllArgsConstructor //lombok. 프로퍼티로 생성자 만들어줌
@RequiredArgsConstructor //lombok. final 있는 필드로만 만들어줌
public class MemberService {

    //프로퍼티에 바로 autowired 하면 단점 -> 못바꿈. 테스트할 때 등 바꿔야 할 때가 있는데 엑세스할 방법이 없음
    //@Autowired
    private final MemberRepository memberRepository; //한번 만들면 중간에 바꿀 일 없음 -> final, 컴파일 때 이상유무 알수 있음

    //@Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        //setter인젝션. setter를 통해 리포지토리를 주입한다. mock도 이 방식으로 넣을 수 있음.
//        //단점 - 어플리케이션 동작중 접근할 수 있게됨. 런타임 실행 후 조작할 일이 없더라도 접근할 수 있다.
//        this.memberRepository = memberRepository;
//    }

//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        //생성자 인젝션. 생성 시점에 주입해주고 중간에 접근 못함.
//        //테스트 때도 주입할 수 있고 의존을 명확히 알 수 있음
//        //요즘은 기본 생성자만 있으면 Autowired 애노테이션 없어도 주입해줌,
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
//중요 -> jpa의 모든 변경, 모든 로직은 트랜잭션 안에서 실행되어야 한다.
//spring제공, jakarta제공 두종류 있음. 이미 spring제공하는 거 많이 쓰기 때문에 spring꺼 쓰는게 낫다.(쓸 수 있는 옵션이 많음)
    @Transactional //따로 쓰면 해당 메서드에 우선권 갖음. 일반 Transactional은 readOnly=false가 기본
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        //jpa에서 em.persist를 하면 영속성 컨텍스트에 멤버 객체를 올린다.
        //그때 영속성 컨텍스트는 키가 id임. -> id값 생성이 보장됨.
        return member.getId();
    }

    //문제있음. 만약 두 유저가 동시에 가입 시도하고 이름이 같아서 validateDuplicateMember를 동시에 통과하면 같은 이름이 두개 들어가게 됨
    //그래서 최후의 방패로 db제약조건에 unique로 방어함.
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}