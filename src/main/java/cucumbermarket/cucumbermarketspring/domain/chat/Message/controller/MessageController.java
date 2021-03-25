package cucumbermarket.cucumbermarketspring.domain.chat.Message.controller;

import cucumbermarket.cucumbermarketspring.domain.chat.Message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
}
