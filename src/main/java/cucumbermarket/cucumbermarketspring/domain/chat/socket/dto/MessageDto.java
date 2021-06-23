package cucumbermarket.cucumbermarketspring.domain.chat.socket.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDto {

    private Long senderId;
    private Long receiverId;
    private String content;
    private Long itemId;
}
