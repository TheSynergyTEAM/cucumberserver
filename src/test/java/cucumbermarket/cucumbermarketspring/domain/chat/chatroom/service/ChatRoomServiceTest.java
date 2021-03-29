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
        Member member = getMember();
        entityManager.persist(member);

        File file = new File("str1", "str2", "str3");
        entityManager.persist(file);
        List<File> fileList = new ArrayList<>();
        fileList.add(file);
        Item item = getItem(member, fileList);
        entityManager.persist(item);
        ChatRoom chatRoom = getChatRoom(member, item);
        chatRoomRepository.save(chatRoom);
        System.out.println("here");
        entityManager.flush();
        //when
        ChatRoom chatRoom1 = chatRoomService.searchChatRoomByMemberAndItem(member, item);

        assertEquals(chatRoom1.getMember(), member);

        //then
    }

    private ChatRoom getChatRoom(Member member, Item item) {
        return new ChatRoom(item, member);
    }

    @Test
    public void addMessageTest() throws Exception {
        Member member = getMember();
        entityManager.persist(member);

        File file = new File("str1", "str2", "str3");
        entityManager.persist(file);
        List<File> fileList = new ArrayList<>();
        fileList.add(file);
        Item item = getItem(member, fileList);
        entityManager.persist(item);
        ChatRoom chatRoom = getChatRoom(member, item);
        entityManager.persist(chatRoom);
        Message message1 = new Message(member, "Hello");
        Message message2 = new Message(member, "World");

        chatRoomService.addMessage(member, item, message1);
        chatRoomService.addMessage(member, item, message2);

        Long id = chatRoom.getId();
        entityManager.persist(chatRoom);

        assertEquals(chatRoom, chatRoomRepository.getOne(id));

        ChatRoom byMemberAndItem = chatRoomRepository.findByMemberAndItem(member, item);
        List<Message> messageList = byMemberAndItem.getMessageList();
        for (Message message : messageList) {
            System.out.println("message.getContent() = " + message.getMember() + ": "+ message.getContent());
        }

    }

    private Item getItem(Member member, List<File> fileList) {
        Item item = new Item(member, "title", Categories.BEAUTY, 1000, "spec", fileList, Boolean.FALSE);
        return item;
    }

    private Member getMember() {
        Member member = new Member(
                "홍길동",
                "1234",
                new Address(),
                LocalDate.now(),
                "abc@abc.com",
                "010-1010-1010",
                0,
                "USER"
        );
        return member;
    }




}