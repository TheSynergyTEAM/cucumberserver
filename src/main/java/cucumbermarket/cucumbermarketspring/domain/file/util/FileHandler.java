package cucumbermarket.cucumbermarketspring.domain.file.util;

import cucumbermarket.cucumbermarketspring.domain.file.Photo;
import cucumbermarket.cucumbermarketspring.domain.file.dto.PhotoDto;
import cucumbermarket.cucumbermarketspring.domain.file.service.PhotoService;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
      //      MultipartHttpServletRequest request
    ) throws Exception{
        List<Photo> fileList = new ArrayList<>();

        if(multipartFiles.isEmpty()){
            return fileList;
        }

        LocalDateTime now = LocalDateTime.now();
     //   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyyMMdd");
        String current_date = now.format(dateTimeFormatter);

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

     //   Iterator<String> iter = request.getFileNames();
     //   while(iter.hasNext()){
        for(MultipartFile multipartFile : multipartFiles){
            if(!multipartFile.isEmpty()){
            String originalFileExtension;
                String contentType = multipartFile.getContentType();

                if(ObjectUtils.isEmpty(contentType)) {
                    break;
                }
                else {
                    if(contentType.contains("image/jpeg"))
                        originalFileExtension = ".jpg";
                    else if(contentType.contains("image/png"))
                        originalFileExtension = ".png";
                    else
                        break;
                }
         //   String uploadFileName = iter.next();
         //   MultipartFile multipartFile = request.getFile(uploadFileName);

            String new_file_name = System.nanoTime()+originalFileExtension;

                PhotoDto photoDto = PhotoDto.builder()
                        .origFileName(multipartFile.getOriginalFilename())
                        .filePath(path + "/" + new_file_name)
                        .build();

          //      photoService.savePhoto(photoDto);
                Photo photo = new Photo(
                        photoDto.getOrigFileName(),
                        photoDto.getFilePath(),
                        photoDto.getFileSize());
                fileList.add(photo);

                file = new File(absolutePath + path + "/" + new_file_name);
                multipartFile.transferTo(file);

                file.setWritable(true);
                file.setReadable(true);
            }
        }

        return fileList;
    }
}
