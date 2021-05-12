package cucumbermarket.cucumbermarketspring.domain.file.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import cucumbermarket.cucumbermarketspring.domain.file.Photo;
import cucumbermarket.cucumbermarketspring.domain.file.PhotoRepository;
import cucumbermarket.cucumbermarketspring.domain.file.QPhoto;
import cucumbermarket.cucumbermarketspring.domain.file.dto.PhotoDto;
import cucumbermarket.cucumbermarketspring.domain.file.dto.PhotoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PhotoService {
    private final PhotoRepository photoRepository;

    @PersistenceContext
    private EntityManager em;

    /**
     * 이미지 저장
     */
    @Transactional
    public Long savePhoto(PhotoDto photoDto){
        return photoRepository.save(photoDto.toEntity()).getId();
    }


    /**
     * 이미지 삭제
     */
    @Transactional
    public void deletePhoto(Long id){
        photoRepository.deleteById(id);
    }

    /**
     * 상품으로 이미지 개별 조회
     */
    @Transactional(readOnly = true)
    public Long findByItemId(Long itemId){

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QPhoto photo = QPhoto.photo;

        Photo file = queryFactory
                .selectFrom(photo)
                .where(photo.item.id.eq(itemId))
                .fetchOne();

        return file.getId();
    }

    /**
     * 이미지 개별 조회
     */
    @Transactional(readOnly = true)
    public PhotoDto findByFileId(Long id){

        Photo entity = photoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));

        PhotoDto photoDto = PhotoDto.builder()
                .origFileName(entity.getOrigFileName())
                .filePath(entity.getFilePath())
                .fileSize(entity.getFileSize())
                .build();

        return photoDto;
    }

    /**
     * 이미지 파일명으로 이미지 개별 조회
     */
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

    /**
     * 이미지 전체 조회
     */
    @Transactional(readOnly = true)
    public List<PhotoResponseDto> findAll(Long itemId){

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QPhoto photo = QPhoto.photo;

        List<Photo> photoList = queryFactory
               .selectFrom(photo)
               .where(photo.item.id.eq(itemId))
               .fetch();

        return photoList.stream()
                .map(PhotoResponseDto::new)
                .collect(Collectors.toList());
    }


}
