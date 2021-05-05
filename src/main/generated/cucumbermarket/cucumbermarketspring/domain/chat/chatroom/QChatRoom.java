package cucumbermarket.cucumbermarketspring.domain.chat.chatroom;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatRoom is a Querydsl query type for ChatRoom
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QChatRoom extends EntityPathBase<ChatRoom> {

    private static final long serialVersionUID = -1927803025L;

    public static final QChatRoom chatRoom = new QChatRoom("chatRoom");

    public final cucumbermarket.cucumbermarketspring.domain.QBaseTimeEntity _super = new cucumbermarket.cucumbermarketspring.domain.QBaseTimeEntity(this);

    public final StringPath chatId = createString("chatId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modified = _super.modified;

    public final NumberPath<Long> receiverId = createNumber("receiverId", Long.class);

    public final NumberPath<Long> senderId = createNumber("senderId", Long.class);

    public QChatRoom(String variable) {
        super(ChatRoom.class, forVariable(variable));
    }

    public QChatRoom(Path<? extends ChatRoom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatRoom(PathMetadata metadata) {
        super(ChatRoom.class, metadata);
    }

}

