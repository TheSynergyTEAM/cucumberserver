package cucumbermarket.cucumbermarketspring.domain.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatNotification {
    private Long senderId;
    private Long receiverId;
    private Long itemId;
}
