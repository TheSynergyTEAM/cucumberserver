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
    private Boolean seller;
    private String avatar;
    private Boolean valid;
    private Boolean completeRoom;

    public ChatRoomListDTO(Long senderId, Long receiverId, Long itemId, String chatId, String senderName, String receiverName, String itemName) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.itemId = itemId;
        ChatId = chatId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.itemName = itemName;
        this.avatar = null;
    }

    public void setLastContent(String lastContent) {
        this.lastContent = lastContent;
    }

    public void setUnreadMessages(int unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    public void setSeller(Boolean seller) {
        this.seller = seller;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public void setCompleteRoom(Boolean completeRoom) {
        this.completeRoom = completeRoom;
    }
}
