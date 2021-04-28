package cucumbermarket.cucumbermarketspring.domain.chat.socket.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateChatRoomDto {

    private Long userid;
    private Long itemid;

    @Builder
    public CreateChatRoomDto(Long userId, Long itemId) {
        this.userid = userId;
        this.itemid = itemId;
    }
}
