package cucumbermarket.cucumbermarketspring.domain.item.controller;

import cucumbermarket.cucumbermarketspring.domain.favourite.service.FavouriteService;
import cucumbermarket.cucumbermarketspring.domain.file.dto.PhotoDto;
import cucumbermarket.cucumbermarketspring.domain.file.dto.PhotoResponseDto;
import cucumbermarket.cucumbermarketspring.domain.file.service.PhotoService;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.item.ItemFileVO;
import cucumbermarket.cucumbermarketspring.domain.item.category.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemListResponseDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemResponseDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemUpdateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.service.ItemService;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import cucumbermarket.cucumbermarketspring.domain.member.address.AddressService;
import cucumbermarket.cucumbermarketspring.domain.member.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemController {
    private final ItemService itemService;
    private final PhotoService fileService;
    private AddressService addressService;
    private final MemberService memberService;
    private final FavouriteService favouriteService;

    /**
     * 물품등록
     */
    @PostMapping("/item")
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUpdateItemResponseDto create(ItemFileVO itemFileVO) throws Exception {

        Member member = memberService.searchMemberById(Long.parseLong(itemFileVO.getId()));
        Address address = new Address(itemFileVO.getCity(), itemFileVO.getStreet1(), "", "");
        Categories category = Categories.find(itemFileVO.getCategory());
        Boolean sold = Boolean.parseBoolean(itemFileVO.getSold());

        ItemCreateRequestDto itemRequestDto = ItemCreateRequestDto.builder()
                        .member(member)
                        .address(address)
                        .title(itemFileVO.getTitle())
                        .categories(category)
                        .spec(itemFileVO.getSpec())
                        .sold(sold)
                        .build();

        Long id = itemService.save(itemRequestDto, itemFileVO.getFiles());

        return new CreateUpdateItemResponseDto(id);
    }

    /**
     * 물품수정
     */
    @PutMapping("/item/{id}")
    @CrossOrigin
    public CreateUpdateItemResponseDto update(@PathVariable Long id, ItemFileVO itemFileVO) throws Exception {
        Address address = new Address(itemFileVO.getCity(), itemFileVO.getStreet1(), "", "");
        Categories category = Categories.find(itemFileVO.getCategory());
        Boolean sold = Boolean.parseBoolean(itemFileVO.getSold());

        ItemUpdateRequestDto itemRequestDto = ItemUpdateRequestDto.builder()
                .address(address)
                .title(itemFileVO.getTitle())
                .categories(category)
                .spec(itemFileVO.getSpec())
                .sold(sold)
                .build();

        List<PhotoResponseDto> dbPhotoList = fileService.findAllByItem(id);
        List<MultipartFile> multipartList = itemFileVO.getFiles();
        List<MultipartFile> validFileList = new ArrayList<>();

        if(CollectionUtils.isEmpty(dbPhotoList)) { // db에 아예 존재 x
            if(!CollectionUtils.isEmpty(multipartList)) { // 전달되어온 파일 o
                for (MultipartFile multipartFile : multipartList)
                    validFileList.add(multipartFile);
            }
        }
        else { // db에 한 장 이상 존재
            if(CollectionUtils.isEmpty(multipartList)) { // 전달되어온 파일 아예 x
                for(PhotoResponseDto dbPhoto : dbPhotoList)
                    fileService.deletePhoto(dbPhoto.getFileid());
            }
            else { // 전달되어온 파일 한 장 이상 존재
                List<String> dbOriginNameList = new ArrayList<>();

                for(PhotoResponseDto dbPhoto : dbPhotoList) {   // db의 파일 원본명 추출
                    PhotoDto dbPhotoDto = fileService.findByFileId(dbPhoto.getFileid());
                    String dbOrigFileName = dbPhotoDto.getOrigFileName();

                    if(!multipartList.contains(dbOrigFileName)) // 삭제요청 파일
                        fileService.deletePhoto(dbPhoto.getFileid());
                    else
                        dbOriginNameList.add(dbOrigFileName);
                }

                for (MultipartFile multipartFile : multipartList) { // 전달되어온 파일 하나씩 검사
                    String multipartOrigName = multipartFile.getOriginalFilename();
                    if(!dbOriginNameList.contains(multipartOrigName)){   // db에 없는 파일이면
                        validFileList.add(multipartFile);
                    }
                }
            }
        }

        itemService.update(id, itemRequestDto, validFileList);

        return new CreateUpdateItemResponseDto(id);
    }

    /**
     * 판매 완료
     */
    @PatchMapping("/item/{id}/soldout")
    @CrossOrigin
    public CreateUpdateItemResponseDto soldOut(@PathVariable("id") Long itemId, @RequestParam("buyer") Long buyerId) {
        itemService.soldOut(itemId, buyerId);
        return new CreateUpdateItemResponseDto(itemId);
    }

    /**
     * 물품삭제
     */
    @DeleteMapping("/item/{id}")
    @CrossOrigin
    public ResponseEntity<?> delete(@PathVariable Long id){
        itemService.delete(id);
        return ResponseEntity.ok().body("OK");
    }

    /**
     * 물품 개별 조회
     */
    @GetMapping("/item/{id}")
    @CrossOrigin
    public ItemResponseDto findById(@PathVariable("id") Long id){
        List<PhotoResponseDto> photoResponseDtoList = fileService.findAllByItem(id);
        List<Long> photoId = new ArrayList<>();
        for(PhotoResponseDto photoResponseDto : photoResponseDtoList)
            photoId.add(photoResponseDto.getFileid());

        Item item = itemService.searchItemById(id);
        itemService.updateViews(id, item.getViews());
        Long favourite = favouriteService.countFavourite(id);
        return itemService.findOne(id, photoId, favourite);
    }

    /**
     * 판매 물품 전체 조회
     */
    @GetMapping("/item/sell")
    @CrossOrigin
    public List<ItemListResponseDto> findBySoldItem(@RequestParam("user") Long memberId) {
        List<Item> itemList = itemService.findBySoldItem(memberId);
        List<ItemListResponseDto> responseDtoList = new ArrayList<>();

        for(Item item : itemList){
            Long favourite = favouriteService.countFavourite(item.getId());
            ItemListResponseDto responseDto = new ItemListResponseDto(item, favourite);
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    /**
     * 구매 물품 전체 조회
     */
    @GetMapping("/item/buy")
    @CrossOrigin
    public List<ItemListResponseDto> findByBoughtItem(@RequestParam("user") Long memberId) {
        List<Item> itemList = itemService.findByBoughtItem(memberId);
        List<ItemListResponseDto> responseDtoList = new ArrayList<>();

        for(Item item : itemList){
            Long favourite = favouriteService.countFavourite(item.getId());
            ItemListResponseDto responseDto = new ItemListResponseDto(item, favourite);
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    /**
     * 물품 전체 조회(구 기준)
     */
    @GetMapping("/item/area")
    @CrossOrigin
    public List<ItemListResponseDto> findByArea(@RequestParam("city") String city, @RequestParam("street") String street) {
        List<Item> itemList = itemService.findByArea(city, street);
        List<ItemListResponseDto> responseDtoList = new ArrayList<>();

        for(Item item : itemList){
            Long favourite = favouriteService.countFavourite(item.getId());
            ItemListResponseDto responseDto = new ItemListResponseDto(item, favourite);
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    /**
     * 물품 전체 조회(카테고리 기준)
     */
    @GetMapping("/item/search/1")
    @CrossOrigin
    public List<ItemListResponseDto> findByCategory(@RequestParam("category") String category) {
        Categories categories = Categories.find(category);
        List<Item> itemList = itemService.findByCategory(categories);
        List<ItemListResponseDto> responseDtoList = new ArrayList<>();

        for(Item item : itemList){
            Long favourite = favouriteService.countFavourite(item.getId());
            ItemListResponseDto responseDto = new ItemListResponseDto(item, favourite);
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    /**
     * 물품 전체 조회(키워드 기준)
     */
    @GetMapping("/item/search/2")
    @CrossOrigin
    public List<ItemListResponseDto> findByKeyword(@RequestParam("keyword") String keyword) {
        List<Item> itemList = itemService.findByKeyword(keyword);
        List<ItemListResponseDto> responseDtoList = new ArrayList<>();

        for(Item item : itemList){
            Long favourite = favouriteService.countFavourite(item.getId());
            ItemListResponseDto responseDto = new ItemListResponseDto(item, favourite);
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    /**
     * 물품 전체 조회
     */
    @GetMapping("/item/list")
    @CrossOrigin
    public List<ItemListResponseDto> findAll() {
        List<Item> itemList = itemService.findAll();
        List<ItemListResponseDto> responseDtoList = new ArrayList<>();

        for(Item item : itemList){
            Long favourite = favouriteService.countFavourite(item.getId());
            ItemListResponseDto responseDto = new ItemListResponseDto(item, favourite);
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }


    @Data
    static class CreateUpdateItemResponseDto {
        private Long id;

        public CreateUpdateItemResponseDto(Long id) {
            this.id = id;
        }
    }

}
