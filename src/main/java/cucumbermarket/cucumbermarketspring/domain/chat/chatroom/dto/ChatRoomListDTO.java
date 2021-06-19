package cucumbermarket.cucumbermarketspring.domain.chat.chatroom.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class ChatRoomListDTO {

    private Long senderId;
    private Long receiverId;
    private Long itemId;
    private String ChatId;
    private String lastContent;
    private int unreadMessages;
    private String senderName;
    private String receiverName;
    private String itemName;

    public ChatRoomListDTO(Long senderId, Long receiverId, Long itemId, String chatId, String senderName, String receiverName, String itemName) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.itemId = itemId;
        ChatId = chatId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.itemName = itemName;
    }

    public void setLastContent(String lastContent) {
        this.lastContent = lastContent;
    }

    public void setUnreadMessages(int unreadMessages) {
        this.unreadMessages = unreadMessages;
    }
}
