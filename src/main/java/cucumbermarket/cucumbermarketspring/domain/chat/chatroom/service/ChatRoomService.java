package cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service;

import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.MessageRepository;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.MessageStatus;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoom;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoomRepository;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.dto.ChatRoomListDTO;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.item.ItemRepository;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    /**
     * 채팅방 생성
     */
    @Transactional
    public String createChatRoom(Long senderId, Long receiverId, Long itemId) {
        var chatId1 = String.format("%s_%s_%s", senderId, receiverId, itemId);
        var chatId2 = String.format("%s_%s_%s", receiverId, senderId, itemId);
        ChatRoom chatRoomBySender = ChatRoom
                .builder()
                .chatId(chatId1)
                .senderId(senderId)
                .receiverId(receiverId)
                .itemId(itemId)
                .valid(Boolean.TRUE)
                .completeRoom(Boolean.FALSE)
                .build();

        ChatRoom chatRoomByReceiver = ChatRoom
                .builder()
                .chatId(chatId2)
                .senderId(receiverId)
                .receiverId(senderId)
                .itemId(itemId)
                .valid(Boolean.TRUE)
                .completeRoom(Boolean.FALSE)
                .build();
        chatRoomRepository.save(chatRoomBySender);
        chatRoomRepository.save(chatRoomByReceiver);
        return chatId1;
    }

    /**
     * 채팅 개별 조회
     *
     * @param senderId
     * @param receiverId
     * @param itemId
     * @return
     */
    @Transactional
    public Optional<String> getChatId(Long senderId, Long receiverId, Long itemId) {
        try {
            String chatId = String.format("%s_%s_%s", senderId, receiverId, itemId);
            try {
                Optional<Item> byId = itemRepository.findById(itemId);
                System.out.println("senderId = " + senderId + ", receiverId = " + receiverId + ", itemId = " + itemId);
                System.out.println("byId = " + byId.get().getTitle());

            } catch (NoSuchElementException emptyItem) {
                return Optional.ofNullable("Empty Item");
            }
            Optional<ChatRoom> bySenderIdAndReceiverId = chatRoomRepository.findByChatId(chatId);
            ChatRoom chatRoom = bySenderIdAndReceiverId.get();
            return Optional.ofNullable(chatRoom.getChatId());

        } catch (NoSuchElementException e) {
            return Optional.ofNullable(createChatRoom(senderId, receiverId, itemId));
        }
    }

    /**
     * 채팅방 전체 조회
     * @param senderId
     * @return
     */
    @Transactional
    public List<ChatRoomListDTO> findAllChatRoomsBySenderId(Long senderId) {
        List<ChatRoom> bySenderId = getChatRoomListBySenderId(senderId);
        List<ChatRoomListDTO> chatRoomList = new ArrayList<ChatRoomListDTO>();

        for (ChatRoom chatRoom : bySenderId) {
            String chatId = chatRoom.getChatId();
            Long itemId = chatRoom.getItemId();
            String senderName, receiverName, itemName;
            Long itemSellerId = 0L;
            senderName = memberRepository.findById(chatRoom.getSenderId()).get().getName();
            try {
                receiverName = memberRepository.findById(chatRoom.getReceiverId()).get().getName();
            } catch (NoSuchElementException e) {
                receiverName = "탈퇴한 회원";
            }
            try {
                Item item = itemRepository.findById(itemId).get();
                itemName = item.getTitle();
                itemSellerId = item.getMember().getId();
            } catch (NoSuchElementException e) {
                itemName = "존재 하지 않는 물건";
                itemSellerId = -1L;
            }
            List<Message> chatRoomMessages = allMessages(chatId);
            Message message = chatRoomMessages.get(chatRoomMessages.size()-1);
            ChatRoomListDTO chatRoomListDTO = new ChatRoomListDTO(
                    chatRoom.getSenderId(), chatRoom.getReceiverId(), itemId, chatId, senderName, receiverName, itemName
            );
            chatRoomListDTO.setLastContent(message.getContent());
            chatRoomListDTO.setUnreadMessages((int) chatRoomMessages.stream().filter(m-> m.getMessageStatus().equals(MessageStatus.UNREAD)).count());
            if (itemSellerId == senderId) {
                chatRoomListDTO.setSeller(Boolean.TRUE);
            } else {
                chatRoomListDTO.setSeller(Boolean.FALSE);
            }
            chatRoomListDTO.setValid(chatRoom.getValid());
            chatRoomListDTO.setCompleteRoom(chatRoom.getCompleteRoom());
            chatRoomList.add(chatRoomListDTO);

        }
        return chatRoomList;
    }

    public List<ChatRoom> getChatRoomListBySenderId(Long senderId) {
        List<ChatRoom> bySenderId = chatRoomRepository.findBySenderId(senderId);
        return bySenderId;
    }

    public List<ChatRoom> getChatRoomListByItemId(Long itemId) {
        List<ChatRoom> byitemId = chatRoomRepository.findByItemId(itemId);
        return byitemId;
    }

    public List<Message> allMessages(String chatId) {
        Pageable pageable = PageRequest.of(0, 1000);
        Page<Message> byChatId = messageRepository.findByChatId(Optional.ofNullable(chatId), pageable);
        return byChatId.getContent();
    }


    @Transactional
    public void updateValid(Long itemId, Long buyerId, Long sellerId) {
        List<ChatRoom> chatRoomList = chatRoomRepository.findByItemId(itemId);
        List<Long> bothId = new ArrayList<>();
        bothId.add(buyerId);
        bothId.add(sellerId);
        for (ChatRoom chatRoom : chatRoomList) {
            chatRoom.updateValid();
            if (bothId.contains(chatRoom.getSenderId()) && bothId.contains(chatRoom.getReceiverId())) {
                chatRoom.updateComplete();
            }
        }
    }

    @Transactional
    public void updateValidByDeletedMember(Long memberId) {
        List<ChatRoom> chatRoomList = getChatRoomListBySenderId(memberId);
        for (ChatRoom chatRoom : chatRoomList) {
            String senderId = String.valueOf(chatRoom.getSenderId());
            String receiverId = String.valueOf(chatRoom.getReceiverId());
            String itemId = String.valueOf(chatRoom.getItemId());
            String mirroredChatId = receiverId + '_' + senderId + '_' + itemId;
            ChatRoom mirroredChatRoom = chatRoomRepository.findByChatId(mirroredChatId).get();
            chatRoom.updateValid();
            mirroredChatRoom.updateValid();
        }
    }

    @Transactional
    public void updateValidByDeletedItem(Long itemId) {
        List<ChatRoom> chatRoomListByItemId = getChatRoomListByItemId(itemId);
        for (ChatRoom chatRoom : chatRoomListByItemId) {
            chatRoom.updateValid();
        }
    }

    public List<ChatRoomListDTO> findAllChatRoomsByItemIdOrMemberName(Long senderId, String keyword) {
        List<ChatRoomListDTO> chatRoomList = findAllChatRoomsBySenderId(senderId);
        if (chatRoomList.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 Sender Id");
        }
        List<ChatRoomListDTO> resultList = new ArrayList<>();
        for (ChatRoomListDTO cr : chatRoomList) {
            if (cr.getItemName().contains(keyword) || cr.getReceiverName().contains(keyword)) {
                resultList.add(cr);
            }
        }
        return resultList;
    }

}