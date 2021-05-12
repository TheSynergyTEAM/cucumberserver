package cucumbermarket.cucumbermarketspring.domain.chat.Message;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long>{

    List<Message> findByChatId(Optional<String> chatId);

    Integer findByChatIdAndMessageStatus(
            Optional<String> chatId, String messageStatus
    );
}
