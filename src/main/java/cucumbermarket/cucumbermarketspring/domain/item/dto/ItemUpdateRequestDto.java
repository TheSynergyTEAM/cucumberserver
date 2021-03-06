package cucumbermarket.cucumbermarketspring.domain.item.dto;

import cucumbermarket.cucumbermarketspring.domain.item.category.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.status.Status;
import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemUpdateRequestDto {
    private String title;
    private Categories categories;
    private int price;
    private String spec;
    private Address address;
    private Status sold;

    @Builder
    public ItemUpdateRequestDto(String title, Categories categories, int price, String spec, Address address, Status sold){
        this.title = title;
        this.categories = categories;
        this.price = price;
        this.spec = spec;
        this.address = address;
        this.sold = sold;
    }
}
