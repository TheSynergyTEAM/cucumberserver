package cucumbermarket.cucumbermarketspring.domain.file.controller;

import cucumbermarket.cucumbermarketspring.domain.file.service.PhotoService;
import cucumbermarket.cucumbermarketspring.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PhotoController {
    private static PhotoService fileService;
    private static ItemService itemService;

    @PostMapping("/photos")
   // @ResponseStatus(HttpStatus.CREATED)
   // public List<String> test(@RequestParam("files") MultipartFile[] files){
    public String test(@RequestParam("files") List<MultipartFile> files) throws Exception{
        String rootPath = FileSystemView.getFileSystemView().getHomeDirectory().toString();
        String basePath = rootPath + "/" + "multi";

        for(MultipartFile file : files){
            String originalName = file.getOriginalFilename();
            String filePath = basePath + "/" + originalName;

            File dest = new File(filePath);
            file.transferTo(dest);
        }

        return  "uploaded";
      /*  try {

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

                PhotoDto fileDto = new PhotoDto();
                fileDto.setOrigFileName(origFilename);
                fileDto.setFileName(filename);
                fileDto.setFilePath(filePath);

                Long photoId = fileService.savePhoto(fileDto);

            }
        } catch(Exception e) {
            e.printStackTrace();
        }*/
     /*   List<String> list = new ArrayList<>();
        for(MultipartFile file : files) {
            String originalfileName = file.getOriginalFilename();
            File dest = new File("C:/Image/" + originalfileName);
            file.transferTo(dest);
        }
        return  list;*/
    }
}
