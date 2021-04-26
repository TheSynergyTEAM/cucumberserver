package cucumbermarket.cucumbermarketspring.domain.file.dto;

import cucumbermarket.cucumbermarketspring.domain.file.Photo;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PhotoDto {
    private String origFileName;
    private String fileName;
    private String filePath;

    @Builder
    public PhotoDto(String origFileName, String fileName, String filePath, Item item){
        this.origFileName = origFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public Photo toEntity(){
        Photo build = Photo.builder()
                .origFileName(origFileName)
                .fileName(fileName)
                .filePath(filePath)
                .build();

        return build;
    }
}
