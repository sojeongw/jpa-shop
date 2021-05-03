package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 컴포넌트 스캔에 의해 자동으로 빈으로 등록된다.
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  @Transactional
  public Long join(Member member) {
    // 중복 회원 검증
    validateDuplicateMember(member);
    memberRepository.save(member);

    return member.getId();
  }

  private void validateDuplicateMember(Member member) {
    // WAS가 동시에 여러 개 떠서 동시에 validate를 시도하면 문제가 생긴다.
    // 실무에서는 이런 멀티 스레드 문제를 해결해줘야 한다.
    List<Member> findMembers = memberRepository.findByName(member.getName());

    if (!findMembers.isEmpty()) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
  }

  public List<Member> findMembers() {
    return memberRepository.findAll();
  }


  public Member findOne(Long memberId) {
    return memberRepository.findOne(memberId);
  }

  @Transactional
  public void update(Long id, String name) {
    Member member = memberRepository.findOne(id);

    // 변경 감지를 사용한다.
    member.setName(name);

    // 조회한 member를 반환해도 되지만 커맨드와 쿼리를 분리하기 위해 하지 않았다.
    // 커맨드는 update를 하기위한 변경성 메서드다.
    // 하지만 member를 리턴하면 조회하는 꼴이 된다. 쿼리와 커맨드가 같이 있게 되는 것이다.
  }
}
