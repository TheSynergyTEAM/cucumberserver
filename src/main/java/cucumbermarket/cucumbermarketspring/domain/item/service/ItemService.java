package cucumbermarket.cucumbermarketspring.domain.item.service;

import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.item.domain.ItemRepository;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemResponseDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public Long save(ItemCreateRequestDto requestDto){
        return itemRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, ItemUpdateRequestDto requestDto){
        Item item = itemRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. id = " + id));

        item.update(requestDto.getTitle(), requestDto.getCategories(), requestDto.getPrice(),
                requestDto.getSpec(), requestDto.getPhoto(), requestDto.getSold());

        return id;
    }

    @Transactional
    public void remove(Long id){
        itemRepository.deleteById(id);
    }

    public ItemResponseDto findOne(Long id){
        Item entity = itemRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. id = " + id));

        return new ItemResponseDto(entity);
    }

    public List<Item> findAll(Long id){
        return itemRepository.findAll();
    }
}
