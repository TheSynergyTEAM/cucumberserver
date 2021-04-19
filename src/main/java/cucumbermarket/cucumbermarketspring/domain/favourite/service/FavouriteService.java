package cucumbermarket.cucumbermarketspring.domain.favourite.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import cucumbermarket.cucumbermarketspring.domain.favourite.FavouriteItem;
import cucumbermarket.cucumbermarketspring.domain.favourite.FavouriteItemRepository;
import cucumbermarket.cucumbermarketspring.domain.favourite.QFavouriteItem;
import cucumbermarket.cucumbermarketspring.domain.favourite.dto.FavItemCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.favourite.dto.FavItemListResponseDto;
import cucumbermarket.cucumbermarketspring.domain.favourite.dto.FavItemResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

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
    public void delete(Long id){
        FavouriteItem favItem = favItemRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 내역이 존재하지 않습니다."));

        favItemRepository.delete(favItem);
    }

    /**
     * 찜하기 모두 조회
     * */
    @Transactional(readOnly = true)
    public List<FavItemListResponseDto> findAll(Long id){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QFavouriteItem favouriteItem = QFavouriteItem.favouriteItem;

        List<FavouriteItem> favouriteList = queryFactory
                .selectFrom(favouriteItem)
                .innerJoin(favouriteItem.member)
                .where(favouriteItem.member.id.eq(id))
                .fetch();

        return favouriteList.stream()
                .map(FavItemListResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 찜하기 개별 조회
     * */
    @Transactional(readOnly = true)
    public FavItemResponseDto findOne(Long id) {
        FavouriteItem entity = favItemRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 내역이 존재하지 않습니다."));

        return new FavItemResponseDto(entity);
    }
}
