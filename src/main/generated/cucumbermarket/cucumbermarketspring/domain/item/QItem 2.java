package cucumbermarket.cucumbermarketspring.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = 1680054039L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItem item = new QItem("item");

    public final cucumbermarket.cucumbermarketspring.domain.QBaseTimeEntity _super = new cucumbermarket.cucumbermarketspring.domain.QBaseTimeEntity(this);

    public final cucumbermarket.cucumbermarketspring.domain.member.address.QAddress address;

    public final EnumPath<cucumbermarket.cucumbermarketspring.domain.item.category.Categories> categories = createEnum("categories", cucumbermarket.cucumbermarketspring.domain.item.category.Categories.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    public final ListPath<cucumbermarket.cucumbermarketspring.domain.favourite.FavouriteItem, cucumbermarket.cucumbermarketspring.domain.favourite.QFavouriteItem> favouriteItem = this.<cucumbermarket.cucumbermarketspring.domain.favourite.FavouriteItem, cucumbermarket.cucumbermarketspring.domain.favourite.QFavouriteItem>createList("favouriteItem", cucumbermarket.cucumbermarketspring.domain.favourite.FavouriteItem.class, cucumbermarket.cucumbermarketspring.domain.favourite.QFavouriteItem.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final cucumbermarket.cucumbermarketspring.domain.member.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modified = _super.modified;

    public final ListPath<cucumbermarket.cucumbermarketspring.domain.file.Photo, cucumbermarket.cucumbermarketspring.domain.file.QPhoto> photo = this.<cucumbermarket.cucumbermarketspring.domain.file.Photo, cucumbermarket.cucumbermarketspring.domain.file.QPhoto>createList("photo", cucumbermarket.cucumbermarketspring.domain.file.Photo.class, cucumbermarket.cucumbermarketspring.domain.file.QPhoto.class, PathInits.DIRECT2);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final cucumbermarket.cucumbermarketspring.domain.review.QReview review;

    public final BooleanPath sold = createBoolean("sold");

    public final StringPath spec = createString("spec");

    public final StringPath title = createString("title");

    public QItem(String variable) {
        this(Item.class, forVariable(variable), INITS);
    }

    public QItem(Path<? extends Item> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItem(PathMetadata metadata, PathInits inits) {
        this(Item.class, metadata, inits);
    }

    public QItem(Class<? extends Item> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new cucumbermarket.cucumbermarketspring.domain.member.address.QAddress(forProperty("address")) : null;
        this.member = inits.isInitialized("member") ? new cucumbermarket.cucumbermarketspring.domain.member.QMember(forProperty("member"), inits.get("member")) : null;
        this.review = inits.isInitialized("review") ? new cucumbermarket.cucumbermarketspring.domain.review.QReview(forProperty("review"), inits.get("review")) : null;
    }

}

