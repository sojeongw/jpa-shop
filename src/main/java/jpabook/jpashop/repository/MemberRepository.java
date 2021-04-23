package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

// 컴포넌트 스캔에 의해 자동으로 빈으로 관리된다.
@Repository
@RequiredArgsConstructor
public class MemberRepository {

  // 스프링이 엔티티 매니저를 주입해준다.
  private final EntityManager em;

  public void save(Member member) {
    em.persist(member);
  }

  public Member findOne(Long id) {
    return em.find(Member.class, id);
  }

  public List<Member> findAll() {
    // 리스트는 jpql을 사용해야 한다.
    // sql은 테이블을 대상으로, jpql은 객체를 대상으로 쿼리한다는 점이 다르다.
    return em.createQuery("select m from Member m", Member.class)
        .getResultList();
  }

  public List<Member> findByName(String name) {
    return em.createQuery("select m from Member m where m.name = :name", Member.class)
        .setParameter("name", name) // :name 파라미터 바인딩
        .getResultList();
  }
}
