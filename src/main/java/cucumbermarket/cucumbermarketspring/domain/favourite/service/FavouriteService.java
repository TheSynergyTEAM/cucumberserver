package cucumbermarket.cucumbermarketspring.domain.favourite.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import cucumbermarket.cucumbermarketspring.domain.favourite.FavouriteItem;
import cucumbermarket.cucumbermarketspring.domain.favourite.FavouriteItemRepository;
import cucumbermarket.cucumbermarketspring.domain.favourite.QFavouriteItem;
import cucumbermarket.cucumbermarketspring.domain.favourite.dto.FavItemCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.category.Categories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FavouriteService {
    private final FavouriteItemRepository favItemRepository;

    @PersistenceContext
    private EntityManager em;

    /**
     * 찜하기생성
     * */
    @Transactional
    public Long create(FavItemCreateRequestDto requestDto){
        return favItemRepository.save(requestDto.toEntity()).getId();
    }

    /**
     * 찜하기 삭제
     * */
    @Transactional
    public void delete(Long itemId, Long memberId){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QFavouriteItem favouriteItem = QFavouriteItem.favouriteItem;

        FavouriteItem favourite = queryFactory
                .selectFrom(favouriteItem)
                .where(favouriteItem.item.id.eq(itemId).and(favouriteItem.member.id.eq(memberId)))
                .fetchOne();

        favItemRepository.delete(favourite);
    }

    /**
     * 찜하기 전체 조회
     * */
    @Transactional(readOnly = true)
    public List<FavouriteItem> findAll(Long memberId){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QFavouriteItem favouriteItem = QFavouriteItem.favouriteItem;

        List<FavouriteItem> favouriteList = queryFactory
                .selectFrom(favouriteItem)
                .where(favouriteItem.member.id.eq(memberId))
                .fetch();

        return favouriteList;
    }

    /**
     * 찜하기 카테고리별 전체 조회
     * */
    @Transactional(readOnly = true)
    public List<FavouriteItem> findAllByCategory(Long memberId, Categories category){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QFavouriteItem favouriteItem = QFavouriteItem.favouriteItem;

        List<FavouriteItem> favouriteList = queryFactory
                .selectFrom(favouriteItem)
                .where(favouriteItem.member.id.eq(memberId).and(favouriteItem.item.categories.eq(category)))
                .fetch();

        return favouriteList;
    }

    /**
     * 찜하기 횟수
     * */
    @Transactional(readOnly = true)
    public Long countFavourite(Long itemId){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QFavouriteItem favouriteItem = QFavouriteItem.favouriteItem;

        Long count = queryFactory
                .selectFrom(favouriteItem)
                .where(favouriteItem.item.id.eq(itemId))
                .fetchCount();

        return count;
    }

    /**
     *  내가 찜하기 누른 상품인지 판별
     */
    @Transactional(readOnly = true)
    public Boolean isItMine(Long loginId, Long itemId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QFavouriteItem favouriteItem = QFavouriteItem.favouriteItem;

        Long count = queryFactory
                .selectFrom(favouriteItem)
                .where(favouriteItem.item.id.eq(itemId).and(favouriteItem.member.id.eq(loginId)))
                .fetchCount();

        if(count == 1)
            return true;
        else
            return false;
    }
}
