package cucumbermarket.cucumbermarketspring.domain.chat.socket.dto;

import lombok.Data;

@Data
public class MessageDto {

    private Long senderId;
    private Long receiverId;
    private String content;
    private Long itemId;
}
