package cucumbermarket.cucumbermarketspring.domain.chat.chatroom;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoom is a Querydsl query type for ChatRoom
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QChatRoom extends EntityPathBase<ChatRoom> {

    private static final long serialVersionUID = -1927803025L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoom chatRoom = new QChatRoom("chatRoom");

    public final cucumbermarket.cucumbermarketspring.domain.QBaseTimeEntity _super = new cucumbermarket.cucumbermarketspring.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final cucumbermarket.cucumbermarketspring.domain.item.QItem item;

    public final cucumbermarket.cucumbermarketspring.domain.member.QMember member;

    public final ListPath<cucumbermarket.cucumbermarketspring.domain.chat.Message.Message, cucumbermarket.cucumbermarketspring.domain.chat.Message.QMessage> messageList = this.<cucumbermarket.cucumbermarketspring.domain.chat.Message.Message, cucumbermarket.cucumbermarketspring.domain.chat.Message.QMessage>createList("messageList", cucumbermarket.cucumbermarketspring.domain.chat.Message.Message.class, cucumbermarket.cucumbermarketspring.domain.chat.Message.QMessage.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modified = _super.modified;

    public QChatRoom(String variable) {
        this(ChatRoom.class, forVariable(variable), INITS);
    }

    public QChatRoom(Path<? extends ChatRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoom(PathMetadata metadata, PathInits inits) {
        this(ChatRoom.class, metadata, inits);
    }

    public QChatRoom(Class<? extends ChatRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new cucumbermarket.cucumbermarketspring.domain.item.QItem(forProperty("item"), inits.get("item")) : null;
        this.member = inits.isInitialized("member") ? new cucumbermarket.cucumbermarketspring.domain.member.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

