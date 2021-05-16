package cucumbermarket.cucumbermarketspring.domain.review;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ReviewFileVO {
    private String itemid;
    private String memberid;
    private String ratingscore;
    private String content;
    private List<MultipartFile> files;
}
