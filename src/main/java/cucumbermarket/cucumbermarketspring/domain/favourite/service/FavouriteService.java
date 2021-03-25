package cucumbermarket.cucumbermarketspring.domain.favourite.service;

import cucumbermarket.cucumbermarketspring.domain.favourite.domain.FavouriteItem;
import cucumbermarket.cucumbermarketspring.domain.favourite.domain.FavouriteItemRepository;
import cucumbermarket.cucumbermarketspring.domain.favourite.dto.FavItemCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FavouriteService {
    private final FavouriteItemRepository favItemRepository;

    @Transactional
    public Long createFavouriteItem(FavItemCreateRequestDto requestDto){
        return favItemRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public void removeFavouriteItem(Long id){
        favItemRepository.deleteById(id);
    }

    public List<FavouriteItem> findAll(Long id){
        return favItemRepository.findAll();
    }
}
