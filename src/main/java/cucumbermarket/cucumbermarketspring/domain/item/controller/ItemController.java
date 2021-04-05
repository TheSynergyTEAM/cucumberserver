package cucumbermarket.cucumbermarketspring.domain.item.controller;

import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemListResponseDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemResponseDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemUpdateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemController {
    private final ItemService itemService;

    /**
     * 물품등록
     */
    @PostMapping("/api/item")
    public Long save(@RequestBody ItemCreateRequestDto requestDto){
        return itemService.save(requestDto);
    }

    /**
     * 물품수정
     */
   @PutMapping("/api/item/{id}")
    public Long update(@PathVariable Long id, @RequestBody ItemUpdateRequestDto requestDto){
        return itemService.update(id, requestDto);
    }

    /**
     * 물품삭제
     */
    @DeleteMapping("/api/item/{id}")
    public Long delete(@PathVariable Long id){
        itemService.delete(id);
        return id;
    }

    /**
     * 물품하나조회
     */
   @GetMapping("/api/item/{id}")
    public ItemResponseDto findById(@PathVariable("id") Long id){
        return itemService.findOne(id);
    }

    /**
     * 물품모두조회
     */
    @GetMapping("/api/item")
    public List<ItemListResponseDto> ItemList(){
        return itemService.findAll();
    }

}
