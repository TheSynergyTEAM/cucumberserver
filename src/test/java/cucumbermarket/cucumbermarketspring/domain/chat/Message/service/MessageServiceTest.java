package cucumbermarket.cucumbermarketspring.domain.chat.Message.service;

import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.MessageRepository;
import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MessageServiceTest {

    @Autowired
    MessageService messageService;
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    public void createMessageTest() throws Exception{

        // given
        Member member = new Member("구형준", "1234", new Address(), LocalDate.now(), "abc@def.com", "010-1234-5678", 5, "USER");
        Message message = new Message(member,"Hello");
        // when
        Long messageId = messageService.createMessage(message);
        entityManager.persist(message);

        // then
        assertEquals(message, messageRepository.getOne(messageId));

    }

    @Test
    public void searchMessage() {

    }
}