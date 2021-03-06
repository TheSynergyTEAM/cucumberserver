package cucumbermarket.cucumbermarketspring.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>{

    Member findByEmail(String email);

    List<Member> findByName(String name);
}
