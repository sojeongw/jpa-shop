package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)  // 스프링과 관련된 테스트를 할 것이라고 알려준다.
@SpringBootTest   // 이것도 함께 넣어줘야 한다.
public class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;

  @Test
  // 추가하지 않으면 No EntityManager with actual transaction available for current thread - cannot reliably process 'persist' call 이라고 에러 발생
  // 테스트가 끝나면 바로 롤백하기 때문에 테이블엔 데이터가 남지 않는다.
  @Transactional
  // 날리고 싶지 않으면 이 옵션을 추가한다.
  @Rollback(value = false)
  public void save() {
    // given
    Member member = new Member();
    member.setUsername("memberA");

    // when
    Long savedId = memberRepository.save(member);
    Member findMember = memberRepository.find(savedId);

    // then
    Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

    // 같은 트랜잭션에 묶여있기 때문에 같은 영속성 컨텍스트에 존재한다.
    // 같은 영속성 컨텍스트에서는 아이디 값이 같으면 같은 엔티티로 식별한다.
    // 이미 같은 영속성 컨텍스트에서 관리되고 있는 엔티티가 있기 때문에 1차 캐시에서 가져온다.
    Assertions.assertThat(findMember).isEqualTo(member);
  }

  @Test
  public void find() {
  }
}