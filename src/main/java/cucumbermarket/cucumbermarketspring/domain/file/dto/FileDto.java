package cucumbermarket.cucumbermarketspring.domain.file.dto;

import cucumbermarket.cucumbermarketspring.domain.file.domain.File;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileDto {
    private String origFileName;
    private String fileName;
    private String filePath;
    private Item item;

    @Builder
    public FileDto(String origFileName, String fileName, String filePath, Item item){
        this.origFileName = origFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.item = item;
    }

    public File toEntity(){
        File build = File.builder()
                .origFileName(origFileName)
                .fileName(fileName)
                .filePath(filePath)
                .item(item)
                .build();

        return build;
    }
}
