package cucumbermarket.cucumbermarketspring.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 939731447L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final cucumbermarket.cucumbermarketspring.domain.member.address.QAddress address;

    public final cucumbermarket.cucumbermarketspring.domain.member.avatar.QAvatar avatar;

    public final DatePath<java.time.LocalDate> birthdate = createDate("birthdate", java.time.LocalDate.class);

    public final StringPath contact = createString("contact");

    public final StringPath email = createString("email");

    public final ListPath<cucumbermarket.cucumbermarketspring.domain.favourite.FavouriteItem, cucumbermarket.cucumbermarketspring.domain.favourite.QFavouriteItem> favouriteItem = this.<cucumbermarket.cucumbermarketspring.domain.favourite.FavouriteItem, cucumbermarket.cucumbermarketspring.domain.favourite.QFavouriteItem>createList("favouriteItem", cucumbermarket.cucumbermarketspring.domain.favourite.FavouriteItem.class, cucumbermarket.cucumbermarketspring.domain.favourite.QFavouriteItem.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<cucumbermarket.cucumbermarketspring.domain.item.Item, cucumbermarket.cucumbermarketspring.domain.item.QItem> item = this.<cucumbermarket.cucumbermarketspring.domain.item.Item, cucumbermarket.cucumbermarketspring.domain.item.QItem>createList("item", cucumbermarket.cucumbermarketspring.domain.item.Item.class, cucumbermarket.cucumbermarketspring.domain.item.QItem.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final NumberPath<Integer> ratingScore = createNumber("ratingScore", Integer.class);

    public final ListPath<cucumbermarket.cucumbermarketspring.domain.review.Review, cucumbermarket.cucumbermarketspring.domain.review.QReview> review = this.<cucumbermarket.cucumbermarketspring.domain.review.Review, cucumbermarket.cucumbermarketspring.domain.review.QReview>createList("review", cucumbermarket.cucumbermarketspring.domain.review.Review.class, cucumbermarket.cucumbermarketspring.domain.review.QReview.class, PathInits.DIRECT2);

    public final ListPath<String, StringPath> roles = this.<String, StringPath>createList("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new cucumbermarket.cucumbermarketspring.domain.member.address.QAddress(forProperty("address")) : null;
        this.avatar = inits.isInitialized("avatar") ? new cucumbermarket.cucumbermarketspring.domain.member.avatar.QAvatar(forProperty("avatar"), inits.get("avatar")) : null;
    }

}

