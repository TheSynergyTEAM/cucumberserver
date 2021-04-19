package cucumbermarket.cucumbermarketspring.domain.item.controller;

import cucumbermarket.cucumbermarketspring.domain.file.service.PhotoService;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemListResponseDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemResponseDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemUpdateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.service.ItemService;
import cucumbermarket.cucumbermarketspring.domain.member.address.AddressService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public CreateItemResponse save(@RequestBody ItemCreateRequestDto requestDto){
   // public Long save(@RequestParam("file") List<MultipartFile> files, @RequestParam("requestParam") ItemCreateRequestDto requestDto){
   /*     Long itemId = itemService.save(requestDto);
        Item item = new Item(requestDto.getMember(), requestDto.getTitle(), requestDto.getCategories(),
                requestDto.getPrice(),  requestDto.getSpec(), requestDto.getAddress(), requestDto.getSold());*/
    /*    Long itemId = itemService.save(requestDto);

        try {
            for(MultipartFile file : files){
                String origFilename = file.getOriginalFilename();
                String filename = new MD5Generator(origFilename).toString();

                String savePath = System.getProperty("user.dir") + "\\files";

                if (!new File(savePath).exists()) {
                    try{
                        new File(savePath).mkdir();
                    }
                    catch(Exception e){
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "\\" + filename;
                file.transferTo(new File(filePath));

                PhotoDto fileDto = PhotoDto.builder()
                        .origFileName(origFilename)
                        .fileName(filename)
                        .filePath(filePath)
                        .build();

                Long fileId = fileService.savePhoto(fileDto);

                PhotoDto photoDto = fileService.getPhoto(fileId);

            //    Photo photo = new Photo(photoDto.getOrigFileName(), photoDto.getFileName(), photoDto.getFilePath());

            }
        } catch(Exception e) {
            e.printStackTrace();
        }*/
        Long id = itemService.save(requestDto);
        return new CreateItemResponse(id);
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
