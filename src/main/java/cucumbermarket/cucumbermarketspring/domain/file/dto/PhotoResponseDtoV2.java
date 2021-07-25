package cucumbermarket.cucumbermarketspring.domain.file.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PhotoResponseDtoV2 {

    String related;
    int total;
    Long relatedId;
    List<byte[]> photoList;
}
