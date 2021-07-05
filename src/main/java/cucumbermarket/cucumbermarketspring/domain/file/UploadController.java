package cucumbermarket.cucumbermarketspring.domain.file;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UploadController {

    private final S3Uploader uploader;

    @PostMapping("/upload/single")
    public String upload(@RequestParam("data") MultipartFile file) throws IOException {
        return uploader.upload(file, "static");
    }

/*    @PostMapping("/upload/multi")
    public String upload2(@RequestParam("data") MultipartFile file) throws IOException {
        return uploader.upload(file, "static");
    }*/
}
