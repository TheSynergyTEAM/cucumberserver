package cucumbermarket.cucumbermarketspring.domain.file.dto;

import cucumbermarket.cucumbermarketspring.domain.file.domain.File;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileDto {
    private String origFileName;
    private String fileName;
    private String filePath;

    @Builder
    public FileDto(String origFileName, String fileName, String filePath){
        this.origFileName = origFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public File toEntity(){
        File build = File.builder()
                .origFileName(origFileName)
                .fileName(fileName)
                .filePath(filePath)
                .build();

        return build;
    }
}
