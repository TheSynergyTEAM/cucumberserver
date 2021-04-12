package cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service;

import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoom;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoomRepository;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoomSearchDTO;
import cucumbermarket.cucumbermarketspring.domain.file.domain.File;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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

//        //given
        Member member = getMember("memberA", "1234", "abc@abc.com", "010-1234-1234");
        entityManager.persist(member);
        Item item1 = getItem1(member);
        File file = new File("str1", "str2", "str3", item1);
        entityManager.persist(file);
        ChatRoom chatRoom = getChatRoom(member, item1);
        chatRoomRepository.save(chatRoom);
        System.out.println("here");
        entityManager.flush();
        //when
        ChatRoom chatRoom1 = chatRoomService.searchChatRoomByMemberAndItem(member, item1);

        assertEquals(chatRoom1.getMember(), member);

        //then
    }

    private Item getItem1(Member member) {
        return new Item(member, "", Categories.BEAUTY, 1, "", new Address(), Boolean.FALSE);
    }

    @Test
    public void addMessageTest() throws Exception {
        Member member = getMember("memberA", "1234", "abc@abc.com", "010-1234-1234");
        entityManager.persist(member);

        File file = new File("str1", "str2", "str3");
        entityManager.persist(file);
        List<File> fileList = new ArrayList<>();
        fileList.add(file);
        Item item = getItem(member, fileList);
        entityManager.persist(item);
        File file = new File("str1", "str2", "str3", item);
        entityManager.persist(file);

        ChatRoom chatRoom = getChatRoom(member, item);
        entityManager.persist(chatRoom);
        Message message1 = new Message(member, "Hello");
        Message message2 = new Message(member, "World");

        chatRoomService.addMessage(member, item, message1);
        chatRoomService.addMessage(member, item, message2);

        Long id = chatRoom.getId();
        entityManager.persist(chatRoom);

        assertEquals(chatRoom, chatRoomRepository.getOne(id));

        List<Message> messageList = chatRoomService.getAllMessage(member, item);
        for (Message message : messageList) {
            System.out.println("message.getContent() = " + message.getMember() + ": "+ message.getContent() + "created at " + message.getCreated());
        }

    }

    @Test
    public void getAllChatRoomByItemTest() throws Exception {

        //given
        Member memberA = getMember("memberA", "1234", "abc@abc.com", "010-1234-1234");
        entityManager.persist(memberA);
        Member memberB = getMember("memberB", "1234", "def@abc.com", "010-1234-1234");
        entityManager.persist(memberB);
        Member memberC = getMember("memberC", "1234", "def@abc.com", "010-1234-1234");
        entityManager.persist(memberC);

        Photo file = new Photo();
        entityManager.persist(file);

        ChatRoom chatRoomA = getChatRoom(memberB, item);
        entityManager.persist(chatRoomA);
        ChatRoom chatRoomB = getChatRoom(memberC, item);
        entityManager.persist(chatRoomB);

        //when
        List<ChatRoom> allChatRoom = chatRoomService.getAllChatRoom(item);


        //then
        Assertions.assertEquals(allChatRoom.size(), 2);
    }

    private Item getItem(Member member, List<Photo> fileList) {
        Item item = new Item();
        return item;
    }

    private Member getMember(String name, String password, String email, String contact) {
        Member member = new Member(
                name,
                password,
                new Address(),
                LocalDate.now(),
                email,
                contact,
                0,
                "USER"
        );
        return member;
    }

    private ChatRoom getChatRoom(Member member, Item item) {
        return new ChatRoom(item, member);
    }


}