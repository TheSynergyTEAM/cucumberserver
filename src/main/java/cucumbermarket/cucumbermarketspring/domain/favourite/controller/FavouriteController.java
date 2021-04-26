package cucumbermarket.cucumbermarketspring.domain.favourite.controller;

import cucumbermarket.cucumbermarketspring.domain.favourite.dto.FavItemCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.favourite.dto.FavItemListResponseDto;
import cucumbermarket.cucumbermarketspring.domain.favourite.dto.FavItemResponseDto;
import cucumbermarket.cucumbermarketspring.domain.favourite.service.FavouriteService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FavouriteController {
    private final FavouriteService favouriteService;

    /**
     * 찜하기생성
     * */
    @PostMapping("/favourite")
    public CreateFavItemResponse create(@RequestBody FavItemCreateRequestDto requestDto){
        Long id = favouriteService.create(requestDto);
        return new CreateFavItemResponse(id);
    }

    /**
     * 찜하기 개별 조회
     * */
    @GetMapping("/favourite/{id}")
    public FavItemResponseDto SearchOne(@PathVariable Long id){
        return favouriteService.findOne(id);
    }

    /**
     * 찜하기 전체 조회
     * */
    @GetMapping("/favourite")
    public List<FavItemListResponseDto> SearchAll(@RequestParam("user") Long id){
        return favouriteService.findAll(id);
    }

    /**
     * 찜하기 삭제
     * */
    @DeleteMapping("/favourite/{id}")
    public void Delete(@PathVariable Long id){
        favouriteService.delete(id);
    }


    @Data
    static class CreateFavItemResponse {
        private Long id;

        public CreateFavItemResponse(Long id) {
            this.id = id;
        }
    }
}
