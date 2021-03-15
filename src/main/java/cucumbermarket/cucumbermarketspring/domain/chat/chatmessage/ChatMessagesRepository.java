package cucumbermarket.cucumbermarketspring.domain.chat.chatmessage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessagesRepository extends JpaRepository<ChatMessages, Long>{
}
