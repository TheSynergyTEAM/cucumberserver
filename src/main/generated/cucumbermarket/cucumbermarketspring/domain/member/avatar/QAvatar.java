package cucumbermarket.cucumbermarketspring.domain.member.avatar;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAvatar is a Querydsl query type for Avatar
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAvatar extends EntityPathBase<Avatar> {

    private static final long serialVersionUID = -1771197999L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAvatar avatar = new QAvatar("avatar");

    public final ArrayPath<byte[], Byte> bytes = createArray("bytes", byte[].class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final cucumbermarket.cucumbermarketspring.domain.member.QMember member;

    public final StringPath name = createString("name");

    public final StringPath path = createString("path");

    public QAvatar(String variable) {
        this(Avatar.class, forVariable(variable), INITS);
    }

    public QAvatar(Path<? extends Avatar> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAvatar(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAvatar(PathMetadata metadata, PathInits inits) {
        this(Avatar.class, metadata, inits);
    }

    public QAvatar(Class<? extends Avatar> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new cucumbermarket.cucumbermarketspring.domain.member.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

