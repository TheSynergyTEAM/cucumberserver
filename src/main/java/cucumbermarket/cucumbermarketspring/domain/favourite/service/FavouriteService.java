package cucumbermarket.cucumbermarketspring.domain.favourite.service;

import cucumbermarket.cucumbermarketspring.domain.favourite.domain.favouriteitem.FavouriteItemRepository;
import cucumbermarket.cucumbermarketspring.domain.favourite.domain.favouritelist.FavouriteListRepository;
import cucumbermarket.cucumbermarketspring.domain.favourite.dto.FavItemCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FavouriteService {
    private final FavouriteItemRepository favItemRepository;
    private final FavouriteListRepository favListRepository;

    @Transactional
    public Long saveItem(FavItemCreateRequestDto requestDto){
        return FavouriteItemRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long deleteItem(Long id){
        favItemRepository.deleteById(id);
    }
}
