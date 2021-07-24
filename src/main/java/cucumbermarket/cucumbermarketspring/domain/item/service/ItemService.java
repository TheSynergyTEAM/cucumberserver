package cucumbermarket.cucumbermarketspring.domain.item.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import cucumbermarket.cucumbermarketspring.aws.BucketName;
import cucumbermarket.cucumbermarketspring.aws.S3Uploader;
import cucumbermarket.cucumbermarketspring.domain.file.Photo;
import cucumbermarket.cucumbermarketspring.domain.file.PhotoRepository;
import cucumbermarket.cucumbermarketspring.domain.file.util.FileHandler;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.item.ItemRepository;
import cucumbermarket.cucumbermarketspring.domain.item.QItem;
import cucumbermarket.cucumbermarketspring.domain.item.category.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemResponseDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemUpdateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.address.QAddress;
import cucumbermarket.cucumbermarketspring.domain.member.avatar.Avatar;
import cucumbermarket.cucumbermarketspring.domain.member.avatar.storage.StorageExtensionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final S3Uploader s3Uploader;
    private final ItemRepository itemRepository;
    private final PhotoRepository photoRepository;
    private final FileHandler fileHandler;

    @PersistenceContext
    private EntityManager em;

    /**
     * 상품등록
     * */
    @Transactional
    public Long save(ItemCreateRequestDto requestDto, List<MultipartFile> files) throws Exception {

        Item newItem = createItem(requestDto);
        int fileNumber = 1;
        for (MultipartFile file : files) {
            saveToS3(newItem, file, fileNumber);
            fileNumber++;
        }

//        List<Photo> photoList = fileHandler.parseFileInfo(newItem, files);
//
//        if(!CollectionUtils.isEmpty(photoList)){
//            for(Photo photo : photoList)
//                newItem.addPhoto(photoRepository.save(photo));
//        }

        return itemRepository.save(newItem).getId();
    }

    private Item createItem(ItemCreateRequestDto requestDto) {
        Item item = new Item(
                requestDto.getMember(),
                null,
                requestDto.getTitle(),
                requestDto.getCategories(),
                requestDto.getPrice(),
                requestDto.getSpec(),
                requestDto.getAddress(),
                requestDto.getSold(),
                0);
        return item;
    }

    /**
     * 상품수정
     * */
    @Transactional
    public void update(Long id, ItemUpdateRequestDto requestDto, List<MultipartFile> files) throws Exception {
        Item item = itemRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        List<Photo> photoList = fileHandler.parseFileInfo(item, files);

        if(!photoList.isEmpty()){
            for(Photo photo : photoList) {
                photoRepository.save(photo);
            }
        }

        item.update(requestDto.getTitle(), requestDto.getCategories(), requestDto.getPrice(),
                requestDto.getSpec(), requestDto.getAddress(), requestDto.getSold());
    }

    /**
     * 판매 완료
     * */
    @Transactional
    public void soldOut(Long itemId, Long buyerId) {
        Item item = itemRepository.findById(itemId).orElseThrow(()
                -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        if (item.getSold() == true) {
            throw new IllegalArgumentException("이미 판매된 상품입니다");
        }
        item.soldOut(true, buyerId);
    }

    /**
     * 상품삭제
     * */
    @Transactional
    public void delete(Long id){
        Item item = itemRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        itemRepository.delete(item);
    }

    /**
     * 상품 개별 조회(정보 + 파일)
     * */
    @Transactional(readOnly = true)
    public ItemResponseDto findOne(Long id, List<Long> fileId, Long count){
        Item entity = itemRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        return new ItemResponseDto(entity, fileId, count);
    }

    /**
     * 상품 개별 조회(정보)
     * */
    @Transactional(readOnly = true)
    public Item searchItemById(Long id){
        Item item = itemRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        return item;
    }

    /**
     * 조회수 증가
     * */
    @Transactional
    public void updateViews(Long id, int views) {
        Item item = itemRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        item.updateView(views);
    }

    /**
     * 판매 상품 전체 조회
     * */
    @Transactional(readOnly = true)
    public List<Item> findBySoldItem(Long memberId){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QItem item = QItem.item;

        List<Item> itemList = queryFactory
                .selectFrom(item)
                .where(item.member.id.eq(memberId))
                .fetch();

        return itemList;
    }

    /**
     * 구매 상품 전체 조회
     * */
    @Transactional(readOnly = true)
    public List<Item> findByBoughtItem(Long buyerId){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QItem item = QItem.item;

        List<Item> itemList = queryFactory
                .selectFrom(item)
                .where(item.buyerId.eq(buyerId))
                .fetch();

        return itemList;
    }

    /**
     * 상품 전체 조회(구 기준)
     * */
   @Transactional(readOnly = true)
   public List<Item> findByArea(String city, String street){
       JPAQueryFactory queryFactory = new JPAQueryFactory(em);

       QItem item = QItem.item;
       QAddress address = QAddress.address;

       List<Item> itemList = queryFactory
               .selectFrom(item)
               .where(item.address.city.eq(city).and(item.address.street1.eq(street)))
               .fetch();

        return itemList;
   }

    /**
     * 상품 전체 조회(카테고리 기준)
     * */
    @Transactional(readOnly = true)
    public List<Item> findByCategory(Categories category){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QItem item = QItem.item;

        List<Item> itemList = queryFactory
                .selectFrom(item)
                .where(item.categories.eq(category))
                .fetch();

        return itemList;
    }

    /**
     * 상품 전체 조회(키워드 기준)
     * */
    @Transactional(readOnly = true)
    public List<Item> findByKeyword(String keyword){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QItem item = QItem.item;

        List<Item> itemList = queryFactory
                .selectFrom(item)
                .where(item.title.contains(keyword).or(item.spec.contains(keyword)))
                .fetch();

        return itemList;
    }

    /**
     * 상품 전체 조회
     * */
    @Transactional(readOnly = true)
    public List<Item> findAll(){

        return itemRepository.findAll();
    }

    public void saveToS3(Item item, MultipartFile file, int fileNumber) {

        String originalFileExtension = "";
        if (file.getContentType().equals("image/jpeg"))
            originalFileExtension = ".jpeg";
        else if (file.getContentType().equals("image/png"))
            originalFileExtension = ".png";
        else if (file.getContentType().equals("image/jpg"))
            originalFileExtension = ".jpg";
        else {
            throw new StorageExtensionException("File Uploaded is not an type of image");
        }
        // get file metadata
        HashMap<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        //Save Image in S3 and then save in the database
        String path = String.format(
                "%s/%s/%s",
                BucketName.TODO_IMAGE.getBucketName(),
                "item",
                UUID.randomUUID());
        String newFileName = "item_" + item.getId() + "_" + fileNumber + originalFileExtension;
        try {
            s3Uploader.upload(path, newFileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        Photo photo = Photo.builder().
                origFileName(newFileName).
                filePath(path).
                fileSize(file.getSize()).build();
        photoRepository.save(photo);

    }

    public List<byte[]> download(Long itemId) {
        Item item = itemRepository.findById(itemId).get();
        List<byte[]> fileList = new ArrayList<>();
        List<Photo> photoList = item.getPhoto();
        if (photoList == null) {
            return fileList;
        }
        for (Photo photo : photoList) {
            byte[] download = s3Uploader.download(photo.getFilePath(), photo.getOrigFileName());
            fileList.add(download);
        }
        return fileList;
    }
}

