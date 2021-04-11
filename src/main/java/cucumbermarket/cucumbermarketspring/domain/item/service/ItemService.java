package cucumbermarket.cucumbermarketspring.domain.item.service;

import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.item.domain.ItemRepository;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemListResponseDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemResponseDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public Long save(ItemCreateRequestDto requestDto){
        return itemRepository.save(requestDto.toEntity()).getId();
    }
    /*public Long save(Item item){
        return itemRepository.save(item).getId();
    }*/

    @Transactional
    public Long update(Long id, ItemUpdateRequestDto requestDto){
        Item item = itemRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        item.update(requestDto.getTitle(), requestDto.getCategories(), requestDto.getPrice(),
                requestDto.getSpec(), requestDto.getAddress(), requestDto.getSold());

        return id;
    }

    @Transactional
    public void delete(Long id){
        Item item = itemRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        itemRepository.delete(item);
    }

    @Transactional(readOnly = true)
    public ItemResponseDto findOne(Long id){
        Item entity = itemRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        return new ItemResponseDto(entity);
    }

   /* @Transactional(readOnly = true)
    public List<ItemListResponseDto> findByArea(String city, String street){
        //Member one = memberRepository.getOne(updateMemberDto.getId());
        one.change(updateMemberDto);//
        List<Item> itemList = itemRepository.findByAddress(city, street);

        return itemRepository.findByArea().stream()
                .map(ItemListResponseDto::new)
                .collect(Collectors.toList());
    }*/

    /*private void validateDuplicateMember(Member member) {

        Member memberByEmail = memberRepository.findByEmail(member.getEmail());
        if (memberByEmail != null) {
            throw new IllegalStateException("중복 회원 존재");
        }
    }*/

    @Transactional(readOnly = true)
    public List<ItemListResponseDto> findAll(){
        return itemRepository.findAll().stream()
                .map(ItemListResponseDto::new)
                .collect(Collectors.toList());
    }
}
