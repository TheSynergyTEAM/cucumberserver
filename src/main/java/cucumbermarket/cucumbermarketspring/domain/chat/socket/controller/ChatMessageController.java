package cucumbermarket.cucumbermarketspring.domain.chat.socket.controller;

import cucumbermarket.cucumbermarketspring.domain.chat.ChatNotification;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.service.MessageService;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoom;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service.ChatRoomService;
import cucumbermarket.cucumbermarketspring.domain.chat.socket.dto.CreateChatRoomDto;
import cucumbermarket.cucumbermarketspring.domain.chat.socket.dto.MessageDto;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.item.ItemRepository;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import cucumbermarket.cucumbermarketspring.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
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

//    @PostMapping("/chat/message")
//    public String findChatRoom(@RequestBody @Valid CreateChatRoomDto request) {
//
//        Long itemId = request.getItemid();
//        Long userId = request.getUserid();
//
//        Member member = memberService.searchMemberById(userId);
//        Item item = itemRepository.getOne(itemId);
//        ChatRoom chatRoom = chatRoomService.searchChatRoomByMemberAndItem(member, item);
////        Long chatRoomId = chatRoomService.createChatRoom(member, item);
//        return "redirect:/room/" + chatRoom.getId();
//    }
//
//    @RequestMapping("/room/{chatRoomId}")
//    public ResponseEntity<?> enterChatRoom(@PathVariable("chatRoomId") Long chatRoomId, HttpServletRequest request) {
//        HttpSession session = request.getSession( );
//        String email = (String)session.getAttribute("email");
//        return ResponseEntity.ok(chatRoomService.getAllMessage(
//                memberRepository.findByEmail(email), chatRoomService.searchChatRoom(chatRoomId).getItem()
//        ));
//    }

    @MessageMapping("/send")
    public void processMessage(@Payload MessageDto messageDto) {

        Optional<String> chatId = chatRoomService.getChatId(
                messageDto.getSenderId(),
                messageDto.getReceiverId(),
                messageDto.getItemId(),
                true
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


}
