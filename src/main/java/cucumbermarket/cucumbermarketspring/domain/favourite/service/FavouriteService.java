package cucumbermarket.cucumbermarketspring.domain.favourite.service;

import cucumbermarket.cucumbermarketspring.domain.favourite.domain.FavouriteItem;
import cucumbermarket.cucumbermarketspring.domain.favourite.domain.FavouriteItemRepository;
import cucumbermarket.cucumbermarketspring.domain.favourite.dto.FavItemSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FavouriteService {
    private final FavouriteItemRepository favItemRepository;

    @Transactional
    public Long createFavouriteItem(FavouriteItem favouriteItem){
        return favItemRepository.save(favouriteItem).getId();
    }
//    public Long createFavouriteItem(FavItemCreateRequestDto requestDto){
//        return favItemRepository.save(requestDto.toEntity()).getId();
//    }

    @Transactional
    public void removeFavouriteItem(Long id){
        FavouriteItem favItem = favItemRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 내역이 존재하지 않습니다."));

        favItemRepository.delete(favItem);
    }

    public List<FavouriteItem> findAll(){
        return favItemRepository.findAll();
    }

    public FavItemSearchDto findOne(Long id) {
        FavouriteItem entity = favItemRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 내역이 존재하지 않습니다."));

        return new FavItemSearchDto(entity);
    }
}
