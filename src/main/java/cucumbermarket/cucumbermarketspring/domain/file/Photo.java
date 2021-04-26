package cucumbermarket.cucumbermarketspring.domain.file;

import cucumbermarket.cucumbermarketspring.domain.item.Item;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "file")
public class Photo {

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
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    private long file_size;

    @Builder
    public Photo(String origFileName, String fileName, String filePath){
        this.origFileName = origFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public void setItem(Item item){
        this.item = item;

        if(!item.getPhoto().contains(this))
            item.getPhoto().add(this);
    }
}