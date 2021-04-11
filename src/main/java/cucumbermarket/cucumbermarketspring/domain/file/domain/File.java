package cucumbermarket.cucumbermarketspring.domain.file.domain;

import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "file")
public class File {

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

    @Builder
    public File(String origFileName, String fileName, String filePath, Item item){
        this.origFileName = origFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.item = item;
    }

    /* public void setItem(Item item) {
        this.item = item;
    }*/
}