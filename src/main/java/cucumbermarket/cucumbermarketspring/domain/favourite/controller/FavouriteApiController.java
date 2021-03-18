package cucumbermarket.cucumbermarketspring.domain.favourite.controller;

import cucumbermarket.cucumbermarketspring.domain.favourite.service.FavouriteService;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemResponseDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class FavouriteApiController {
    private final FavouriteService favouriteService;

    @PostMapping("/favourite")
    public Long save(@RequestBody FavouriteCreateRequestDto requestDto){
        return favouriteService.save(requestDto);
    }


    @GetMapping("/favourite/{id}")
    public FavouriteResponseDto findById(@PathVariable Long id){
        return FavouriteService.findById(id);
    }
}
