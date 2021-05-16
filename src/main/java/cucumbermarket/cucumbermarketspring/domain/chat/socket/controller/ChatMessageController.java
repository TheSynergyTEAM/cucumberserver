package cucumbermarket.cucumbermarketspring.domain.chat.socket.controller;

import cucumbermarket.cucumbermarketspring.domain.chat.ChatNotification;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.service.MessageService;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoom;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.dto.ChatRoomListDTO;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service.ChatRoomService;
import cucumbermarket.cucumbermarketspring.domain.chat.socket.dto.CreateChatRoomDto;
import cucumbermarket.cucumbermarketspring.domain.chat.socket.dto.MessageDto;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.item.ItemRepository;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import cucumbermarket.cucumbermarketspring.domain.member.service.MemberService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private final ChatRoomService chatRoomService;
    @Autowired
    private final MessageService messageService;
    @Autowired
    private final MemberService memberService;

    @MessageMapping("/send")
    @CrossOrigin
    public void processMessage(@Payload MessageDto messageDto) {

        System.out.println("messageDto = " + messageDto);
        Optional<String> chatId = chatRoomService.getChatId(
                messageDto.getSenderId(),
                messageDto.getReceiverId(),
                messageDto.getItemId()
        );
        Message message = messageService.createMessage(messageDto);
        Member sender = memberService.searchMemberById(message.getSenderId());
        simpMessagingTemplate.convertAndSendToUser(
                String.valueOf(message.getReceiverId()), "/queue/messages",
                new ChatNotification(
                        message.getId(),
                        message.getSenderId(),
                        sender.getEmail()));
    }

    @GetMapping("/message/{senderId}/{receiverId}/{itemId}")
    @CrossOrigin
    public ResponseEntity<?> findChatMessages(@PathVariable("senderId") Long senderId, @PathVariable("receiverId") Long receiverId, @PathVariable("itemId") Long itemId){
        List<Message> messages = messageService.findMessages(senderId, receiverId, itemId);
        messageService.updateMessages(senderId, receiverId, itemId);
        return ResponseEntity.ok(
                messages
        );
    }

    @RequestMapping("/test")
    @CrossOrigin
    public String testview(){
        return "test";
    }

    @GetMapping("/message/{senderId}/{receiverId}/{itemId}/count")
    @CrossOrigin
    public ResponseEntity<?> countNewMessages(@PathVariable("senderId") Long senderId, @PathVariable("receiverId") Long receiverId, @PathVariable("itemId") Long itemId) {
        return ResponseEntity.ok(
                new CountMessageDTO(messageService.countNewMessages(senderId, receiverId, itemId))
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

    @Getter
    class CountMessageDTO {
        private Long count;

        public CountMessageDTO(Long count) {
            this.count = count;
        }
    }

}
