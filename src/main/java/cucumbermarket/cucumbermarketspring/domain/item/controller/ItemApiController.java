package cucumbermarket.cucumbermarketspring.domain.item.controller;

import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemResponseDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemUpdateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ItemApiController {
    private final ItemService itemService;

    @PostMapping("/api/item")
    public Long save(@RequestBody ItemCreateRequestDto requestDto){
        return itemService.save(requestDto);
    }

    @PutMapping("/api/item/{id}")
    public Long update(@PathVariable Long id, @RequestBody ItemUpdateRequestDto requestDto){
        return itemService.update(id, requestDto);
    }

    @DeleteMapping("/api/item/{id}")
    public Long delete(@PathVariable Long id){
        itemService.delete(id);
        return id;
    }

    @GetMapping("/item/{id}")
    public ItemResponseDto findById(@PathVariable Long id){
        return itemService.findOne(id);
    }

    @GetMapping("/item/{id}")
    public ItemResponseDto findAll(@PathVariable Long id){
        return itemService.findOne(id);
    }

}
