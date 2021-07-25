package cucumbermarket.cucumbermarketspring.domain.favourite.controller;

import cucumbermarket.cucumbermarketspring.domain.favourite.FavouriteItem;
import cucumbermarket.cucumbermarketspring.domain.favourite.dto.FavItemCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.favourite.dto.FavItemListResponseDto;
import cucumbermarket.cucumbermarketspring.domain.favourite.service.FavouriteService;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.item.category.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.service.ItemService;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FavouriteController {
    private final FavouriteService favouriteService;
    private final ItemService itemService;
    private final MemberService memberService;

    /**
     * 찜하기생성
     * */
    @PostMapping("/favourite")
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    public CreateFavItemResponseDto create(@RequestBody CreateFavItemRequestDto createRequest){
        Item item = itemService.searchItemById(createRequest.getItemid());
        Member member = memberService.searchMemberById(createRequest.getMemberid());

        FavItemCreateRequestDto requestDto = FavItemCreateRequestDto.builder()
                .item(item)
                .member(member)
                .build();

        Long id = favouriteService.create(requestDto);
        return new CreateFavItemResponseDto(id);
    }

    /**
     * 찜하기 전체 조회
     * */
    @GetMapping("/favourite")
    @CrossOrigin
    public List<FavItemListResponseDto> SearchAll(@RequestParam("user") Long memberId){
        List<FavouriteItem> favouriteItemList = favouriteService.findAll(memberId);
        List<FavItemListResponseDto> responseDtoList = new ArrayList<>();

        for(FavouriteItem favouriteItem : favouriteItemList){
            Long itemId = favouriteItem.getItem().getId();
            Long count = favouriteService.countFavourite(itemId);
            responseDtoList.add(new FavItemListResponseDto(favouriteItem, count));
        }

        return responseDtoList;
    }

    /**
     * 찜하기 카테고리별 전체 조회
     * */
    @GetMapping("/favourite/search")
    @CrossOrigin
    public List<FavItemListResponseDto> SearchAllByCategory(@RequestParam("user") Long memberId, @RequestParam("category") String value){
        Categories category = Categories.find(value);
        List<FavouriteItem> favouriteItemList = favouriteService.findAllByCategory(memberId, category);
        List<FavItemListResponseDto> responseDtoList = new ArrayList<>();

        for(FavouriteItem favouriteItem : favouriteItemList){
            Long itemId = favouriteItem.getItem().getId();
            Long count = favouriteService.countFavourite(itemId);
            responseDtoList.add(new FavItemListResponseDto(favouriteItem, count));
        }

        return responseDtoList;
    }

    /**
     * 찜하기 삭제
     * */
    @DeleteMapping("/favourite/{itemId}/{memberId}")
    @CrossOrigin
    public ResponseEntity<?> delete(@PathVariable("itemId") Long itemId, @PathVariable("memberId") Long memberId){
        favouriteService.delete(itemId, memberId);
        return ResponseEntity.ok().body("OK");
    }


    @Data
    static class CreateFavItemResponseDto {
        private Long id;

        public CreateFavItemResponseDto(Long id) {
            this.id = id;
        }
    }

    @Data
    static class CreateFavItemRequestDto{
        @NotEmpty
        private Long itemid;
        private Long memberid;
    }
}
