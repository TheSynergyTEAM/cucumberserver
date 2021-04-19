package cucumbermarket.cucumbermarketspring.domain.file.dto;

import cucumbermarket.cucumbermarketspring.domain.file.Photo;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PhotoDto {
    private String origFileName;
    private String fileName;
    private String filePath;
    private Item item;

    @Builder
    public PhotoDto(String origFileName, String fileName, String filePath, Item item){
        this.origFileName = origFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.item = item;
    }

    public Photo toEntity(){
        Photo build = Photo.builder()
                .origFileName(origFileName)
                .fileName(fileName)
                .filePath(filePath)
                .item(item)
                .build();

        return build;
    }
}
