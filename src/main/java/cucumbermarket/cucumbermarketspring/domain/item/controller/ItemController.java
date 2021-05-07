package cucumbermarket.cucumbermarketspring.domain.item.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemController {
    private final ItemService itemService;
    private PhotoService fileService;
    private AddressService addressService;
    private final MemberService memberService;

    /**
     * 물품등록
     */
    @PostMapping("/item")
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    public CreateItemResponse create(ItemFileVO itemFileVO) throws Exception {
  //  public ResponseEntity create(ItemFileVO itemFileVO) throws Exception {

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
        return new CreateItemResponse(id);

    /*    HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "localhost:3000/item/" + id);
        return new ResponseEntity(headers, HttpStatus.CREATED);*/
    }

    /**
     * 물품수정
     */
    @PutMapping("/item/{id}")
    @CrossOrigin
    public ItemResponseDto update(@PathVariable Long id, ItemFileVO itemFileVO) throws Exception {
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

        return itemService.update(id, itemRequestDto, itemFileVO.getFiles());
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
        return itemService.findOne(id);
    }

   // @GetMapping(value = "/item/{id}",
   //         produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    /*@GetMapping("/item/{id}")
    public ResponseEntity<?> findById(
            @PathVariable("id") Long id,
            HttpServletRequest request) throws MalformedURLException {
        List<Photo> photoList = fileService.findAll(id);
        for(Photo photo : photoList){
            Path filePath = Paths.get(photo.getFilePath()).toAbsolutePath().normalize();
            Resource resource = new UrlResource(filePath.toUri());

            String contentType = null;

            try{
                contentType = request.getServletContext().getMimeType(
                        resource.getFile().getAbsolutePath()
                );
            } catch (IOException e) {
                e.printStackTrace();
                contentType = "application/octet-stream";
            }
        }

        ItemResponseDto responseDto = itemService.findOne(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + mdssFile.getName() + "\""
                )
                .body(resource);
    }*/

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
    @GetMapping("/item/list")
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
