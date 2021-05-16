package cucumbermarket.cucumbermarketspring.domain.item.controller;

import cucumbermarket.cucumbermarketspring.domain.file.dto.PhotoDto;
import cucumbermarket.cucumbermarketspring.domain.file.dto.PhotoResponseDto;
import cucumbermarket.cucumbermarketspring.domain.file.service.PhotoService;
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

    /**
     * 물품등록
     */
    @PostMapping("/item")
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUpdateItemResponse create(ItemFileVO itemFileVO) throws Exception {

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

        return new CreateUpdateItemResponse(id);
    }

    /**
     * 물품수정
     */
    @PutMapping("/item/{id}")
    @CrossOrigin
    public CreateUpdateItemResponse update(@PathVariable Long id, ItemFileVO itemFileVO) throws Exception {
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

        return new CreateUpdateItemResponse(id);
    }

    /**
     * 물품삭제
     */
    @DeleteMapping("/item/{id}")
    @CrossOrigin
    public void delete(@PathVariable Long id){
        itemService.delete(id);
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

        ItemResponseDto itemResponseDto = itemService.findOne(id, null);
        itemService.updateViews(id, itemResponseDto.getViews());
        return itemService.findOne(id, photoId);
    }


    /**
     * 물품 전체 조회(구 기준)
     */
    @GetMapping("/item/area")
    @CrossOrigin
    public List<ItemListResponseDto> findByArea(
            @RequestParam("city") String city,
            @RequestParam("street") String street) {
        return itemService.findByArea(city, street);
    }

    /**
     * 물품 전체 조회
     */
    @GetMapping("/item/list")
    @CrossOrigin
    public List<ItemListResponseDto> findAll() {
        return itemService.findAll();
    }


    @Data
    static class CreateUpdateItemResponse {
        private Long id;

        public CreateUpdateItemResponse(Long id) {
            this.id = id;
        }
    }

}
