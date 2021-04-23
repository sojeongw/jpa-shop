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
}
