package cucumbermarket.cucumbermarketspring.domain.file.util;

import cucumbermarket.cucumbermarketspring.domain.file.Photo;
import cucumbermarket.cucumbermarketspring.domain.file.PhotoRepository;
import cucumbermarket.cucumbermarketspring.domain.file.dto.PhotoDto;
import cucumbermarket.cucumbermarketspring.domain.file.service.PhotoService;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
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
    private final PhotoRepository photoRepository;

    public FileHandler(PhotoService photoService, PhotoRepository photoRepository) {
        this.photoService = photoService;
        this.photoRepository = photoRepository;
    }

    public List<Photo> parseFileInfo(
            Item item,
            List<MultipartFile> multipartFiles
    ) throws Exception {
        List<Photo> fileList = new ArrayList<>();

      //  if (multipartFiles == null) // 전달되어온 파일이 존재하지 않을 경우
      //      return fileList;
        if(!CollectionUtils.isEmpty(multipartFiles)) {  // 전달되어 온 파일이 존재할 경우
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

         //   String absolutePath = new File("").getAbsolutePath() + "\\";
            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

         //   String path = "images/" + current_date;
            String path = "images" + File.separator + current_date;
            File file = new File(path);
            if (!file.exists()) {
                boolean wasSuccessful = file.mkdirs();
                if (!wasSuccessful)
                    System.out.println("file: was not successful");
            }

            for (MultipartFile multipartFile : multipartFiles) {
                    String originalFileExtension;
                    String contentType = multipartFile.getContentType();

                    if (ObjectUtils.isEmpty(contentType)) {
                        break;
                    } else {
                        if (contentType.contains("image/jpeg"))
                            originalFileExtension = ".jpg";
                        else if (contentType.contains("image/png"))
                            originalFileExtension = ".png";
                        else
                            break;
                    }

                    String new_file_name = System.nanoTime() + originalFileExtension;

                    PhotoDto photoDto = PhotoDto.builder()
                            .origFileName(multipartFile.getOriginalFilename())
                            //.filePath(path + "/" + new_file_name)
                            .filePath(path + File.separator + new_file_name)
                            .fileSize(multipartFile.getSize())
                            .build();

                    Photo photo = new Photo(
                            photoDto.getOrigFileName(),
                            photoDto.getFilePath(),
                            photoDto.getFileSize());
                    if(item.getId() != null)
                        photo.setItem(item);
                    fileList.add(photo);

                 //   file = new File(absolutePath + path + "/" + new_file_name);
                    file = new File(absolutePath + path + File.separator + new_file_name);
                    multipartFile.transferTo(file);

                    file.setWritable(true);
                    file.setReadable(true);

            }

        }

        return fileList;
    }
}
