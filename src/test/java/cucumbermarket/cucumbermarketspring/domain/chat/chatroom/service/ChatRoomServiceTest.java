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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

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

//        //given
        Member member = getMember("memberA", "1234", "abc@abc.com", "010-1234-1234");
        entityManager.persist(member);
        Item item1 = getItem1(member);
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



    }

    @Test
    public void getAllChatRoomByItemTest() throws Exception {


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