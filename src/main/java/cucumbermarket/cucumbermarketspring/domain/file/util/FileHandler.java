package cucumbermarket.cucumbermarketspring.domain.file.util;

import cucumbermarket.cucumbermarketspring.domain.file.Photo;
import cucumbermarket.cucumbermarketspring.domain.file.PhotoRepository;
import cucumbermarket.cucumbermarketspring.domain.file.dto.PhotoDto;
import cucumbermarket.cucumbermarketspring.domain.file.service.PhotoService;
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
    private final PhotoRepository photoRepository;

    public FileHandler(PhotoService photoService, PhotoRepository photoRepository) {
        this.photoService = photoService;
        this.photoRepository = photoRepository;
    }

    public List<Photo> parseFileInfo(
            Long itemId,
            List<MultipartFile> multipartFiles
    ) throws Exception {
        List<Photo> fileList = new ArrayList<>();

        if(multipartFiles == null)
            return  fileList;
        else {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

            String absolutePath = new File("").getAbsolutePath() + "\\";

            String path = "images/" + current_date;
            File file = new File(path);
            if (!file.exists()) {
                boolean wasSuccessful = file.mkdirs();
                if (!wasSuccessful)
                    System.out.println("file: was not successful");
            }

            for (MultipartFile multipartFile : multipartFiles) {
                if(multipartFile.isEmpty()) {
                    Photo deleteFile;
                    String originName = multipartFile.getOriginalFilename();
                    deleteFile = photoService.findByFileName(originName, itemId);
                    photoRepository.delete(deleteFile);
                }
                else {
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
                            .filePath(path + "/" + new_file_name)
                            .fileSize(multipartFile.getSize())
                            .build();

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

        }
        return fileList;
    }
}
