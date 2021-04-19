package cucumbermarket.cucumbermarketspring.domain.file.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PhotoController {
  /*  private static PhotoService fileService;
    private static ItemService itemService;

    @PostMapping("/photos")
    public String test(@RequestParam("itemId") Long id, @RequestParam("file") List<MultipartFile> files){

        ItemResponseDto itemResponseDto = itemService.findOne(id);
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


            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }*/
}
