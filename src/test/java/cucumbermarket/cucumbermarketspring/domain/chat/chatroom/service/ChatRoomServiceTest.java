package cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service;

import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoom;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoomRepository;
import cucumbermarket.cucumbermarketspring.domain.file.Photo;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.item.category.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.service.ItemService;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import cucumbermarket.cucumbermarketspring.domain.member.service.MemberService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ChatRoomServiceTest {

    @Autowired
    ChatRoomService chatRoomService;
    @Autowired
    ItemService itemService;
    @Autowired
    MemberService memberService;
    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    public void createChatRoomTest() throws Exception {

        //given
        String chatRoomId = chatRoomService.createChatRoom(1L, 2L, 1L);

        //when
        Optional<ChatRoom> byChatId = chatRoomRepository.findByChatId(chatRoomId);

        //then
        Assertions.assertEquals(chatRoomId, byChatId.get().getChatId());

    }

    @Test
    public void findChatRoomBySenderIdTest() throws Exception {

        //given
        String chatRoomId = chatRoomService.createChatRoom(1L, 2L, 1L);
        String chatRoomId2 = chatRoomService.createChatRoom(2L, 3L, 3L);

        //when
        List<ChatRoom> bySenderId = chatRoomRepository.findBySenderId(1L);

        //then
        Assertions.assertEquals(bySenderId.size(),1);
    }


}