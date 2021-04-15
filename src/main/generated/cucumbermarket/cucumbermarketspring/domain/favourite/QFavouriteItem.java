package cucumbermarket.cucumbermarketspring.domain.favourite;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavouriteItem is a Querydsl query type for FavouriteItem
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFavouriteItem extends EntityPathBase<FavouriteItem> {

    private static final long serialVersionUID = 1947348192L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavouriteItem favouriteItem = new QFavouriteItem("favouriteItem");

    public final cucumbermarket.cucumbermarketspring.domain.QBaseTimeEntity _super = new cucumbermarket.cucumbermarketspring.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final cucumbermarket.cucumbermarketspring.domain.item.QItem item;

    public final cucumbermarket.cucumbermarketspring.domain.member.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modified = _super.modified;

    public QFavouriteItem(String variable) {
        this(FavouriteItem.class, forVariable(variable), INITS);
    }

    public QFavouriteItem(Path<? extends FavouriteItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavouriteItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavouriteItem(PathMetadata metadata, PathInits inits) {
        this(FavouriteItem.class, metadata, inits);
    }

    public QFavouriteItem(Class<? extends FavouriteItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new cucumbermarket.cucumbermarketspring.domain.item.QItem(forProperty("item"), inits.get("item")) : null;
        this.member = inits.isInitialized("member") ? new cucumbermarket.cucumbermarketspring.domain.member.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

