package cucumbermarket.cucumbermarketspring.domain.file.dto;

import cucumbermarket.cucumbermarketspring.domain.file.Photo;
import lombok.Getter;

@Getter
public class PhotoResponseDto {
    private Long fileid;

    public PhotoResponseDto(Photo entity){
        this.fileid = entity.getId();
    }
}
