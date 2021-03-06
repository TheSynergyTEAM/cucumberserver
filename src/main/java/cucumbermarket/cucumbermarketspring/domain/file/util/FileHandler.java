package cucumbermarket.cucumbermarketspring.domain.file.util;

import cucumbermarket.cucumbermarketspring.aws.BucketName;
import cucumbermarket.cucumbermarketspring.aws.S3Uploader;
import cucumbermarket.cucumbermarketspring.domain.file.Photo;
import cucumbermarket.cucumbermarketspring.domain.file.PhotoRepository;
import cucumbermarket.cucumbermarketspring.domain.file.dto.PhotoDto;
import cucumbermarket.cucumbermarketspring.domain.file.service.PhotoService;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.member.avatar.storage.StorageExtensionException;
import cucumbermarket.cucumbermarketspring.domain.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RequiredArgsConstructor
public class FileHandler {

//    private final PhotoService photoService;
//    private final PhotoRepository photoRepository;


    public List<Photo> parseFileInfo(
            Item item,
            List<MultipartFile> multipartFiles
    ) throws Exception {
        List<Photo> fileList = new ArrayList<>();

        if(!CollectionUtils.isEmpty(multipartFiles)) {  // 전달되어 온 파일이 존재할 경우
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

            String path = "images" + File.separator + "item" + File.separator + current_date;
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

                    file = new File(absolutePath + path + File.separator + new_file_name);
                    multipartFile.transferTo(file);

                    file.setWritable(true);
                    file.setReadable(true);

            }

        }

        return fileList;
    }

    public List<Photo> parseFileInfo(
            Review review,
            List<MultipartFile> multipartFiles
    ) throws Exception {
        List<Photo> fileList = new ArrayList<>();

        if(!CollectionUtils.isEmpty(multipartFiles)) {  // 전달되어 온 파일이 존재할 경우
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

            String path = "images" + File.separator + "review" + File.separator + current_date;
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
                        .filePath(path + File.separator + new_file_name)
                        .fileSize(multipartFile.getSize())
                        .build();

                Photo photo = new Photo(
                        photoDto.getOrigFileName(),
                        photoDto.getFilePath(),
                        photoDto.getFileSize());
                if(review.getId() != null)
                   photo.setReview(review);
                fileList.add(photo);

                file = new File(absolutePath + path + File.separator + new_file_name);
                multipartFile.transferTo(file);

                file.setWritable(true);
                file.setReadable(true);

            }

        }

        return fileList;
    }

}
