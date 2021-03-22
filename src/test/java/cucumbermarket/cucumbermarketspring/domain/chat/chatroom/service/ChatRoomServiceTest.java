package cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service;

import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoomRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ChatRoomServiceTest {

    @Autowired
    ChatRoomService chatRoomService;
    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    public void createChatRoomTest() throws Exception {

        //given

        //when

        //then
    }

}