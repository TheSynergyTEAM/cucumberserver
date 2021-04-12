package cucumbermarket.cucumbermarketspring.domain.file.service;

import cucumbermarket.cucumbermarketspring.domain.file.Photo;
import cucumbermarket.cucumbermarketspring.domain.file.PhotoRepository;
import cucumbermarket.cucumbermarketspring.domain.file.dto.PhotoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PhotoService {
    private PhotoRepository photoRepository;

    @Transactional
    public Long savePhoto(PhotoDto photoDto){
        return photoRepository.save(photoDto.toEntity()).getId();
    }
    /*public FileDto saveFile(FileDto fileDto){
        Long id = fileRepository.save(fileDto.toEntity()).getId();
        return getFile(id);
    }*/

    @Transactional
    public void removePhoto(Long id){
        photoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PhotoDto getPhoto(Long id){
        Photo photo = photoRepository.findById(id).get();

        PhotoDto photoDto = PhotoDto.builder()
                .origFileName(photo.getOrigFileName())
                .fileName(photo.getFileName())
                .filePath(photo.getFilePath())
                .build();

        return photoDto;
    }

    @Transactional(readOnly = true)
    public List<Photo> findAll(Long id){
        return photoRepository.findAll();
    }
}
