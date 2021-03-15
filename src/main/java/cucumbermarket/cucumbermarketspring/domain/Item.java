package cucumbermarket.cucumbermarketspring.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;
    private LocalDateTime registerDate;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int price;
    private String spec;

    @OneToMany(mappedBy = "item")
    private List<UploadFile> photos;

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public Category getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public String getSpec() {
        return spec;
    }

    public List<UploadFile> getPhotos() {
        return photos;
    }
}
