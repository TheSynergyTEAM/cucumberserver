package cucumbermarket.cucumbermarketspring.domain.file.controller;

import cucumbermarket.cucumbermarketspring.domain.file.dto.PhotoDto;
import cucumbermarket.cucumbermarketspring.domain.file.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@RestController
public class PhotoController {

    private final PhotoService photoService;

    /**
     * 썸네일용 이미지
     */
    @CrossOrigin
    @GetMapping(
            value = "/thumbnail/{id}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<byte[]> getThumbnail(@PathVariable Long id) throws IOException {
       // String absolutePath = new File("").getAbsolutePath() + "\\";
        String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;
        String path;

        if(id != 0) {
            PhotoDto photoDto = photoService.findByFileId(id);
            path = photoDto.getFilePath();
        }
        else
           // path = "images/thumbnail/thumbnail.png";
            path = "images" + File.separator + "thumbnail" + File.separator + "thumbnail.png";

        InputStream imageStream = new FileInputStream(absolutePath + path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }

    /**
     * 이미지 개별 조회
     */
    @CrossOrigin
    @GetMapping(
            value = "/image/{id}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
        PhotoDto photoDto = photoService.findByFileId(id);
      //  String absolutePath = new File("").getAbsolutePath() + "\\";
        String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;
        String path = photoDto.getFilePath();

        InputStream imageStream = new FileInputStream(absolutePath + path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }

    /**
     * 이미지 전체 조회
     */
  //  @CrossOrigin
  //  @GetMapping("/images/{id}")
  //  public List<PhotoResponseDto> getImageList(@PathVariable Long id) {
  //      return photoService.findAll(id);
  //  }
}
