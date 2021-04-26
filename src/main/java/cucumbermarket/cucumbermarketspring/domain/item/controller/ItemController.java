package cucumbermarket.cucumbermarketspring.domain.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumbermarket.cucumbermarketspring.domain.file.service.PhotoService;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemListResponseDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemResponseDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemUpdateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.service.ItemService;
import cucumbermarket.cucumbermarketspring.domain.member.address.AddressService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemController {
    private final ItemService itemService;
    private PhotoService fileService;
    private AddressService addressService;

    /**
     * 물품등록
     */
    //  public Long save(@RequestParam("file") List<MultipartFile> files, @RequestParam("requestParam") ItemCreateRequestDto requestDto){
    @PostMapping("/item")
    @CrossOrigin
    public CreateItemResponse create(
   // public ResponseEntity create(
    /*        @Valid @RequestParam("member") Member member,
            @Valid @RequestParam("address") Address address,
            @Valid @RequestParam("title") String title,
            @Valid @RequestParam("categories") Categories categories,
            @Valid @RequestParam("spec") String spec,
            @Valid @RequestParam("sold") Boolean sold,*/
            @Valid @RequestParam("requestDto") String requestDto,
            @Valid @RequestParam("files") List<MultipartFile> files
    ) throws Exception {

        Item item = new ObjectMapper().readValue(requestDto, Item.class);
        ItemCreateRequestDto ItemRequestDto =
                ItemCreateRequestDto.builder()
                        .member(item.getMember())
                        .address(item.getAddress())
                        .title(item.getTitle())
                        .categories(item.getCategories())
                        .spec(item.getSpec())
                        .sold(item.getSold())
                        .build();


        Long id = itemService.save(ItemRequestDto, files);
        return new CreateItemResponse(id);
       // return ResponseEntity.created(linkTo(ItemController.class).slash(id).toUri()).build();
    }

    /**
     * 물품수정
     */
   @PutMapping("/item/{id}")
    public ItemResponseDto update(@PathVariable Long id, @RequestBody ItemUpdateRequestDto requestDto){
        return itemService.update(id, requestDto);
    }

    /**
     * 물품삭제
     */
    @DeleteMapping("/item/{id}")
    public void delete(@PathVariable Long id){
        itemService.delete(id);
    }

    /**
     * 물품 개별 조회
     */
    @GetMapping("/item/{id}")
    public ItemResponseDto findById(@PathVariable("id") Long id){
        return itemService.findOne(id);
    }

    /**
     * 물품 전체 조회(구 기준)
     */
    @GetMapping("/item/area")
    public List<ItemListResponseDto> findByArea(
            @RequestParam("city") String city,
            @RequestParam("street") String street) {

        return itemService.findByArea(city, street);
    }

    /**
     * 물품 전체 조회
     */
    @GetMapping("/item")
    public List<ItemListResponseDto> findAll() {
        return itemService.findAll();
    }


    @Data
    static class CreateItemResponse {
        private Long id;

        public CreateItemResponse(Long id) {
            this.id = id;
        }
    }

}
