package cucumbermarket.cucumbermarketspring.domain.file.dto;

import cucumbermarket.cucumbermarketspring.domain.file.Photo;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PhotoDto {
    private String origFileName;
    private String filePath;
    private Long fileSize;

    @Builder
    public PhotoDto(String origFileName, String filePath, Long fileSize){
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public Photo toEntity(){
        Photo build = Photo.builder()
                .origFileName(origFileName)
                .filePath(filePath)
                .fileSize(fileSize)
                .build();

        return build;
    }
}
