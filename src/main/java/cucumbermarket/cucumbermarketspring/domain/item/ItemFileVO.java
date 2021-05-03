package cucumbermarket.cucumbermarketspring.domain.item;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ItemFileVO {
    private String id;
    private String city;
    private String street1;
    private String title;
    private String category;
    private String price;
    private String spec;
    private String sold;
    private List<MultipartFile> files;
}