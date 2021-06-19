package cucumbermarket.cucumbermarketspring.domain.chat.socket.dto;

import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import lombok.Data;

import java.util.List;

@Data
public class ChatRoomMessagesDTO {
    String currentPage;
    String totalPages;
    String totalMessages;
    List<Message> contents;

    public ChatRoomMessagesDTO(String currentPage, String totalPages, String totalMessages, List<Message> contents) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalMessages = totalMessages;
        this.contents = contents;
    }
}
