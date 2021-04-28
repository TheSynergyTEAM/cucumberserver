package cucumbermarket.cucumbermarketspring.domain.chat.socket.controller;

import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoom;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service.ChatRoomService;
import cucumbermarket.cucumbermarketspring.domain.chat.socket.dto.CreateChatRoomDto;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.item.ItemRepository;
import cucumbermarket.cucumbermarketspring.domain.item.service.ItemService;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import cucumbermarket.cucumbermarketspring.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {


    @Autowired
    private final ChatRoomService chatRoomService;
    @Autowired
    private final MemberService memberService;

    @Autowired
    private final MemberRepository memberRepository;
    //    @Autowired
//    private final ItemService itemService;
    @Autowired
    private final ItemRepository itemRepository;



    @PostMapping("/chat/message")
    public String createChatRoom(@RequestBody @Valid CreateChatRoomDto request) {

        Long itemId = request.getItemid();
        Long userId = request.getUserid();

        Member member = memberService.searchMemberById(userId);
        Item item = itemRepository.getOne(itemId);
        Long chatRoomId = chatRoomService.createChatRoom(member, item);
        return "redirect:/room/" + chatRoomId;
    }

    @RequestMapping("/room/{chatRoomId}")
    public String enterChatRoom(@PathVariable("chatRoomId") Long chatRoomId, HttpServletRequest request) {
        HttpSession session = request.getSession();

        String email = (String)session.getAttribute("email");
        Member memberByEmail = memberRepository.findByEmail(email);
        ChatRoom chatRoom = chatRoomService.searchChatRoom(chatRoomId);
        List<Message> messageList = chatRoom.getMessageList();
//        Collections.reverse(messageList);


    }

}
