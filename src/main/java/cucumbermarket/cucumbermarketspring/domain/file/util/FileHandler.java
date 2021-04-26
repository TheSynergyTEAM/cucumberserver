package cucumbermarket.cucumbermarketspring.domain.file.util;

import cucumbermarket.cucumbermarketspring.domain.file.Photo;
import cucumbermarket.cucumbermarketspring.domain.file.dto.PhotoDto;
import cucumbermarket.cucumbermarketspring.domain.file.service.PhotoService;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileHandler {

    private final PhotoService photoService;

    public FileHandler(PhotoService photoService) {
        this.photoService = photoService;
    }

    public List<Photo> parseFileInfo(
            Item item,
            List<MultipartFile> multipartFiles
    ) throws Exception{
        List<Photo> fileList = new ArrayList<>();

        if(multipartFiles.isEmpty()){
            return fileList;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(("yyyyMMdd"));
        String current_date = simpleDateFormat.format(LocalDate.now());

        String absolutePath = new File("").getAbsolutePath() + "\\";

        String path = "images/" + current_date;
        File file = new File(path);
        if(!file.exists()){
            boolean wasSuccessful = file.mkdirs();
            if(!wasSuccessful)
                System.out.println("file: was not successful");
            else
                System.out.println("file: 디렉토리 생성");
        }

        for(MultipartFile multipartFile : multipartFiles){
            if(!multipartFile.isEmpty()){
                String contentType = multipartFile.getContentType();
                String originalFileExtension;

                if(ObjectUtils.isEmpty(contentType)) {
                    break;
                }
                else {
                    if(contentType.contains("image/jpeg"))
                        originalFileExtension = ".jpg";
                    else if(contentType.contains("image/png"))
                        originalFileExtension = ".png";
                    else if(contentType.contains("image/gif"))
                        originalFileExtension = ".gif";
                    else
                        break;
                }

                String new_file_name = System.nanoTime() + originalFileExtension;

                PhotoDto photoDto = PhotoDto.builder()
                        .item(item)
                        .origFileName(multipartFile.getOriginalFilename())
                        .filePath(path + "/" + new_file_name)
                        .build();

                photoService.savePhoto(photoDto);

                file = new File(absolutePath + path + "/" + new_file_name);
                multipartFile.transferTo(file);

                file.setWritable(true);
                file.setReadable(true);
            }
        }

        return fileList;
    }
}
