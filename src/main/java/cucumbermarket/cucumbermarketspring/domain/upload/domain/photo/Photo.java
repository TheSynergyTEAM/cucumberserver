package cucumbermarket.cucumbermarketspring.upload.domain.photo;

import cucumbermarket.cucumbermarketspring.domain.upload.file.File;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File file;
}