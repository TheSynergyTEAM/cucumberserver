package cucumbermarket.cucumbermarketspring.domain.file;

import cucumbermarket.cucumbermarketspring.domain.BaseTimeEntity;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.review.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "file")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Photo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @ManyToOne
    //@JoinColumn(name = "item_id")
    @JoinTable(
            name = "ITEM_PHOTO",
            joinColumns = @JoinColumn(name = "file_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private Item item;

    @ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "review_id")
    @JoinTable(
            name = "REVIEW_PHOTO",
            joinColumns = @JoinColumn(name = "file_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id")
    )
    private Review review;

    @Column(nullable = false)
    private String origFileName;

    @Column(nullable = false)
    private String filePath;

    private Long fileSize;



    @Builder
    public Photo(String origFileName, String filePath, Long fileSize){
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public void setItem(Item item){
        this.item = item;

        if(!item.getPhoto().contains(this))
            item.getPhoto().add(this);
    }

    public void setReview(Review review){
        this.review = review;

        if(!review.getPhoto().contains(this))
            review.getPhoto().add(this);
    }
}