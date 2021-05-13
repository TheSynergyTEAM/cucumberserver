package cucumbermarket.cucumbermarketspring.domain.review.controller;

import cucumbermarket.cucumbermarketspring.domain.file.dto.PhotoDto;
import cucumbermarket.cucumbermarketspring.domain.file.dto.PhotoResponseDto;
import cucumbermarket.cucumbermarketspring.domain.file.service.PhotoService;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.item.service.ItemService;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.service.MemberService;
import cucumbermarket.cucumbermarketspring.domain.review.ReviewFileVO;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewListResponseDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewResponseDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewUpdateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.review.service.ReviewService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewService reviewService;
    private final ItemService itemService;
    private final PhotoService fileService;
    private final MemberService memberService;

    /**
     * 리뷰 생성
     * */
    @PostMapping("/review")
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUpdateReviewResponse save(ReviewFileVO reviewFileVO) throws Exception {
        Item item = itemService.searchItemById(Long.parseLong(reviewFileVO.getItemid()));
        Member writer = memberService.searchMemberById(Long.parseLong(reviewFileVO.getMemberid()));
        int ratingScore = Integer.parseInt(reviewFileVO.getRatingscore());

        ReviewCreateRequestDto reviewRequestDto = ReviewCreateRequestDto.builder()
                .item(item)
                .member(writer)
                .ratingScore(ratingScore)
                .content(reviewFileVO.getContent())
                .build();

        Long id = reviewService.createReview(reviewRequestDto, reviewFileVO.getFiles());

        return new CreateUpdateReviewResponse(id);
    }

    /**
     * 리뷰 수정
     * */
    @PutMapping("/review/{id}")
    @CrossOrigin
    public CreateUpdateReviewResponse update(@PathVariable Long id, ReviewFileVO reviewFileVO) throws Exception {

        int ratingScore = Integer.parseInt(reviewFileVO.getRatingscore());

        ReviewUpdateRequestDto reviewRequestDto = ReviewUpdateRequestDto.builder()
                .content(reviewFileVO.getContent())
                .ratingScore(ratingScore)
                .build();

        List<PhotoResponseDto> dbPhotoList = fileService.findAllByReview(id);
        List<MultipartFile> multipartList = reviewFileVO.getFiles();
        List<MultipartFile> validFileList = new ArrayList<>();

        if(CollectionUtils.isEmpty(dbPhotoList)) {
            if(!CollectionUtils.isEmpty(multipartList)) {
                for (MultipartFile multipartFile : multipartList)
                    validFileList.add(multipartFile);
            }
        }
        else {
            if(CollectionUtils.isEmpty(multipartList)) {
                for(PhotoResponseDto dbPhoto : dbPhotoList)
                    fileService.deletePhoto(dbPhoto.getFileid());
            }
            else {
                List<String> dbOriginNameList = new ArrayList<>();

                for(PhotoResponseDto dbPhoto : dbPhotoList) {
                    PhotoDto dbPhotoDto = fileService.findByFileId(dbPhoto.getFileid());
                    String dbOrigFileName = dbPhotoDto.getOrigFileName();

                    if(!multipartList.contains(dbOrigFileName))
                        fileService.deletePhoto(dbPhoto.getFileid());
                    else
                        dbOriginNameList.add(dbOrigFileName);
                }

                for (MultipartFile multipartFile : multipartList) {
                    String multipartOrigName = multipartFile.getOriginalFilename();
                    if(!dbOriginNameList.contains(multipartOrigName)){
                        validFileList.add(multipartFile);
                    }
                }
            }
        }

        reviewService.updateReview(id, reviewRequestDto, validFileList);

        return new CreateUpdateReviewResponse(id);
    }

    /**
     * 리뷰 삭제
     * */
    @DeleteMapping("/review/{id}")
    @CrossOrigin
    public void delete (@PathVariable Long id){
        reviewService.delete(id);
    }

    /**
     * 리뷰 개별 조회
     * */
    @GetMapping("/review/{id}")
    @CrossOrigin
    public ReviewResponseDto searchOne(@PathVariable Long id){
        List<PhotoResponseDto> photoResponseDtoList = fileService.findAllByReview(id);
        List<Long> photoId = new ArrayList<>();

        for(PhotoResponseDto photoResponseDto : photoResponseDtoList)
            photoId.add(photoResponseDto.getFileid());

        return reviewService.findOne(id, photoId);
    }

    /**
     * 리뷰 전체 조회
     * */
    @GetMapping("/review/list/all")
    @CrossOrigin
    public List<ReviewListResponseDto> searchAll(@RequestParam("user") Long id){
        return reviewService.findAll(id);
    }

    /**
     * 판매 리뷰 전체 조회
     * */
    @GetMapping("/review/list/sell")
    @CrossOrigin
    public List<ReviewListResponseDto> searchAllSold(@RequestParam("user") Long id){
        return reviewService.findAllBySeller(id);
    }

    /**
     * 구매 리뷰 전체 조회
     * */
    @GetMapping("/review/list/buy")
    @CrossOrigin
    public List<ReviewListResponseDto> searchAllBought(@RequestParam("user") Long id){
        return reviewService.findAllByBuyer(id);
    }


    @Data
    static class CreateUpdateReviewResponse {
        private Long id;

        public CreateUpdateReviewResponse(Long id) {
            this.id = id;
        }
    }
}
