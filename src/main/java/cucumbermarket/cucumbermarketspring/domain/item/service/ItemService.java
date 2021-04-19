package cucumbermarket.cucumbermarketspring.domain.item.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.item.ItemRepository;
import cucumbermarket.cucumbermarketspring.domain.item.QItem;
import cucumbermarket.cucumbermarketspring.domain.item.dto.*;
import cucumbermarket.cucumbermarketspring.domain.member.address.QAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @PersistenceContext
    private EntityManager em;

    /**
     * 상품등록
     * */
    @Transactional
    public Long save(ItemCreateRequestDto requestDto){

        return itemRepository.save(requestDto.toEntity()).getId();
    }

    /**
     * 상품수정
     * */
    @Transactional
    public ItemResponseDto update(Long id, ItemUpdateRequestDto requestDto){
        Item item = itemRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        item.update(requestDto.getTitle(), requestDto.getCategories(), requestDto.getPrice(),
                requestDto.getSpec(), requestDto.getAddress(), requestDto.getSold());

        ItemResponseDto itemResponseDto = this.findOne(id);
        return itemResponseDto;
    }

    /**
     * 상품삭제
     * */
    @Transactional
    public void delete(Long id){
        Item item = itemRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        itemRepository.delete(item);
    }

    /**
     * 상품 개별 조회
     * */
    @Transactional(readOnly = true)
    public ItemResponseDto findOne(Long id){
        Item entity = itemRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        return new ItemResponseDto(entity);
    }

    /**
     * 상품 전체 조회(구 기준)
     * */
   @Transactional(readOnly = true)
    public List<ItemListResponseDto> findByArea(String city, String street){
       JPAQueryFactory queryFactory = new JPAQueryFactory(em);

       QItem item = QItem.item;
       QAddress address = QAddress.address;

       List<Item> itemList = queryFactory
               .selectFrom(item)
               .on(item.address.city.eq(city).and(item.address.street1.eq(street)))
               .fetch();

       return itemList.stream()
               .map(ItemListResponseDto::new)
               .collect(Collectors.toList());
    }

    /**
     * 상품 전체 조회
     * */
    @Transactional(readOnly = true)
    public List<ItemListResponseDto> findAll(){
        return itemRepository.findAll().stream()
                .map(ItemListResponseDto::new)
                .collect(Collectors.toList());
    }
}
