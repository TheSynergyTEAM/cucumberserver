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

    public MessageDto(
            Long senderId,
            Long receiverId,
            String content,
            Long itemId)
    {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.itemId = itemId;
    }

    public MessageDto() {
    }
}
