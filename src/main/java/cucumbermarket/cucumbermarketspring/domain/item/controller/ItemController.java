package cucumbermarket.cucumbermarketspring.domain.item.controller;

import cucumbermarket.cucumbermarketspring.domain.file.service.FileService;
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
    private FileService fileService;

    /**
     * 물품등록
     */
    //  public Long save(@RequestParam("file") List<MultipartFile> files, @RequestParam("requestParam") ItemCreateRequestDto requestDto){
    @PostMapping("/api/item")
    public Long save(@RequestBody ItemCreateRequestDto requestDto){
   /*     Long itemId = itemService.save(requestDto);
        Item item = new Item(requestDto.getMember(), requestDto.getTitle(), requestDto.getCategories(),
                requestDto.getPrice(),  requestDto.getSpec(), requestDto.getAddress(), requestDto.getSold());*/

       /* try {
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

                FileDto fileDto = FileDto.builder()
                        .origFileName(origFilename)
                        .fileName(filename)
                        .filePath(filePath)
                        .item(item)
                        .build();

                Long fileId = fileService.saveFile(fileDto);
                FileDto photo = fileService.getFile(fileId);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } */
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
    public void delete(@PathVariable Long id){
        itemService.delete(id);
    }

    /**
     * 물품하나조회
     */
    @GetMapping("/api/item/{id}")
    public ItemResponseDto findById(@PathVariable("id") Long id){
        return itemService.findOne(id);
    }

    /**
     * 구로 조회
     */
   /* @GetMapping("/api/item")
    public List<ItemListResponseDto> findByArea(
            @RequestParam(value = "city", required = true) String param1,
            @RequestParam(value = "street", required = true) String param2) {
        String city = addressService.requestCity();
        return itemService.findByArea();
    }*/

    /**
     * 물품모두조회
     */
    @GetMapping("/api/item")
    public List<ItemListResponseDto> findAll(){
        return itemService.findAll();
    }

}
