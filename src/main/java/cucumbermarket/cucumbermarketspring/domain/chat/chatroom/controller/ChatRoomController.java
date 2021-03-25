package cucumbermarket.cucumbermarketspring.domain.chat.chatroom.controller;

import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
}
