package cucumbermarket.cucumbermarketspring.domain.chat.Message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long>{

//    List<Message> findByChatId(Optional<String> chatId);

    Page<Message> findByChatId(Optional<String> chatId, Pageable pageable);
}
