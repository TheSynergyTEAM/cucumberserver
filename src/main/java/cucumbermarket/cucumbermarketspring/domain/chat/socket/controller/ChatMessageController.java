package cucumbermarket.cucumbermarketspring.domain.chat.socket.controller;

import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.service.MessageService;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.dto.ChatRoomListDTO;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service.ChatRoomService;
import cucumbermarket.cucumbermarketspring.domain.chat.socket.dto.ChatRoomMessagesDTO;
import cucumbermarket.cucumbermarketspring.domain.chat.socket.dto.MessageDto;
import cucumbermarket.cucumbermarketspring.domain.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatRoomService chatRoomService;
    private final MessageService messageService;

    @MessageMapping("/chat")
    @CrossOrigin
    public void processMessage(@Payload MessageDto messageDto) throws IOException {
        try {
            messageService.createMessage(messageDto);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }

    @GetMapping("/message/{senderId}/{receiverId}/{itemId}")
    @CrossOrigin
    public ResponseEntity<?> findChatMessages(@PathVariable("senderId") Long senderId,
                                              @PathVariable("receiverId") Long receiverId,
                                              @PathVariable("itemId") Long itemId,
                                              @RequestParam(defaultValue = "0") int page){
        ChatRoomMessagesDTO chatRoomMessagesDTO = messageService.findMessages(senderId, receiverId, itemId, page);
        messageService.updateMessages(senderId, receiverId, itemId);
        return ResponseEntity.ok(
                chatRoomMessagesDTO
        );
    }

    @GetMapping("/message/{senderId}/{receiverId}/{itemId}/v2")
    @CrossOrigin
    public ResponseEntity<?> findChatMessagesWithoutPage(@PathVariable("senderId") Long senderId,
                                              @PathVariable("receiverId") Long receiverId,
                                              @PathVariable("itemId") Long itemId){
        List<Message> messagesWithoutPage = messageService.findMessagesWithoutPage(senderId, receiverId, itemId);
        messageService.updateMessages(senderId, receiverId, itemId);
        return ResponseEntity.ok(
                messagesWithoutPage
        );
    }

    @RequestMapping("/test")
    @CrossOrigin
    public String testview(){
        return "test";
    }

    @GetMapping("/message/{senderId}/count")
    @CrossOrigin
    public ResponseEntity<?> countNewMessages(@PathVariable("senderId") Long senderId) {
        return ResponseEntity.ok(
                new CountMessageDTO(messageService.countNewMessages(senderId))
        );
    }

    @GetMapping("/chatroom/{senderId}")
    @CrossOrigin
    public ResponseEntity<?> chatRoomList(@PathVariable("senderId") Long senderId) {
        List<ChatRoomListDTO> allChatRoomsBySenderId = chatRoomService.findAllChatRoomsBySenderId(senderId);
        return ResponseEntity.ok().body(
                allChatRoomsBySenderId
        );
    }

    @GetMapping("/chatroom")
    @CrossOrigin
    public ResponseEntity<?> chatRoomListByItemIdOrMemberId(
            @RequestParam(value = "senderId") Long senderId,
            @RequestParam(value = "itemName", required = false, defaultValue = "") String itemName,
            @RequestParam(value = "memberName", required = false, defaultValue = "") String memberName) {
        if (itemName.equals("") && memberName.equals("")) {
            return ResponseEntity.badRequest().body("검색 결과가 존재하지 않습니다.");
        }
        try {
            List<ChatRoomListDTO> chatRoomList = chatRoomService.findAllChatRoomsByItemIdOrMemberName
                    (senderId, itemName, memberName);
            return ResponseEntity.ok().body(
                    chatRoomList
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("검색 결과가 존재하지 않습니다.");
        }
    }

    @Getter
    class CountMessageDTO {
        private Long count;

        public CountMessageDTO(Long count) {
            this.count = count;
        }
    }

}
