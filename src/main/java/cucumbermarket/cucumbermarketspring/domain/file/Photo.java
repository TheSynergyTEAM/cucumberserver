package cucumbermarket.cucumbermarketspring.domain.file;

import cucumbermarket.cucumbermarketspring.domain.BaseTimeEntity;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "file")
public class Photo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

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
}