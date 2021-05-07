package cucumbermarket.cucumbermarketspring.domain.file.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import cucumbermarket.cucumbermarketspring.domain.file.Photo;
import cucumbermarket.cucumbermarketspring.domain.file.PhotoRepository;
import cucumbermarket.cucumbermarketspring.domain.file.QPhoto;
import cucumbermarket.cucumbermarketspring.domain.file.dto.PhotoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PhotoService {
    private final PhotoRepository photoRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Long savePhoto(PhotoDto photoDto){
        return photoRepository.save(photoDto.toEntity()).getId();
    }

    @Transactional
    public void removePhoto(Long id){
        photoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PhotoDto findByItemId(Long itemId){
        /*Photo photo = photoRepository.findById(id).get();

        PhotoDto photoDto = PhotoDto.builder()
                .origFileName(photo.getOrigFileName())
                .filePath(photo.getFilePath())
                .fileSize(photo.getFileSize())
                .build();

        return photoDto;*/

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QPhoto photo = QPhoto.photo;

        Photo file = queryFactory
                .selectFrom(photo)
                .where(photo.item.id.eq(itemId))
                .fetchFirst();

        PhotoDto photoDto = PhotoDto.builder()
                .origFileName(file.getOrigFileName())
                .filePath(file.getFilePath())
                .fileSize(file.getFileSize())
                .build();

        return photoDto;
    }

    @Transactional(readOnly = true)
    public Photo findByFileName(String fileName, Long itemId){

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QPhoto photo = QPhoto.photo;

        Photo file = queryFactory
                .selectFrom(photo)
                .where(photo.item.id.eq(itemId).and(photo.origFileName.eq(fileName)))
                .fetchOne();

        return file;
    }

    @Transactional(readOnly = true)
    public List<Photo> findAll(Long itemId){

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QPhoto photo = QPhoto.photo;

        List<Photo> photoList = queryFactory
               .selectFrom(photo)
               .where(photo.item.id.eq(itemId))
               .fetch();

        return photoRepository.findAll();
    }
}
