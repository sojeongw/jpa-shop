package jpabook.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

  // 스프링 컨테이너가 EntityManager를 주입할 수 있게 해주는 애너테이션
  @PersistenceContext
  private EntityManager em;

  public Long save(Member member) {
    em.persist(member);

    // 커맨드와 쿼리를 분리하자.
    // 저장은 사이드 이펙트를 일으키는 커맨드 성이기 때문에 리턴을 안 하거나 아이디 정도만 반환한다.
    return member.getId();
  }

  public Member find(Long id) {
    return em.find(Member.class, id);
  }
}
