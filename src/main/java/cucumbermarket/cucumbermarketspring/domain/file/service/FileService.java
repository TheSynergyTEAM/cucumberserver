package cucumbermarket.cucumbermarketspring.domain.file.service;

import cucumbermarket.cucumbermarketspring.domain.file.domain.File;
import cucumbermarket.cucumbermarketspring.domain.file.domain.FileRepository;
import cucumbermarket.cucumbermarketspring.domain.file.dto.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FileService {
    private FileRepository fileRepository;

    @Transactional
    public Long saveFile(FileDto fileDto){
        return fileRepository.save(fileDto.toEntity()).getId();
    }
    /*public FileDto saveFile(FileDto fileDto){
        Long id = fileRepository.save(fileDto.toEntity()).getId();
        return getFile(id);
    }*/

    @Transactional
    public void removeFile(Long id){
        fileRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public FileDto getFile(Long id){
        File file = fileRepository.findById(id).get();

        FileDto fileDto = FileDto.builder()
                .origFileName(file.getOrigFileName())
                .fileName(file.getFileName())
                .filePath(file.getFilePath())
                .build();

        return fileDto;
    }

    @Transactional(readOnly = true)
    public List<File> findAll(Long id){
        return fileRepository.findAll();
    }
}
