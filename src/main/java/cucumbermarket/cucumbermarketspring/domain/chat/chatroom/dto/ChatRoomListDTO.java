package cucumbermarket.cucumbermarketspring.domain.chat.chatroom.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class ChatRoomListDTO {

    private String ChatId;

    public ChatRoomListDTO(String chatId) {
        ChatId = chatId;
    }
}
