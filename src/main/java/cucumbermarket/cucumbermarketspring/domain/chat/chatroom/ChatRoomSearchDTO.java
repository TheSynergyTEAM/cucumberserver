package cucumbermarket.cucumbermarketspring.domain.chat.chatroom;

import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import lombok.Data;

@Data
public class ChatRoomSearchDTO {
    private Item item;
    private Member member;
}
