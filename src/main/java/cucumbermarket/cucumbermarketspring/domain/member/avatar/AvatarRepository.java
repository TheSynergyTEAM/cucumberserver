package cucumbermarket.cucumbermarketspring.domain.member.avatar;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Avatar findByName(String name);
}
