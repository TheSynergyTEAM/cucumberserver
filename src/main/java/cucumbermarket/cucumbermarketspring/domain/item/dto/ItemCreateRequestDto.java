package cucumbermarket.cucumbermarketspring.domain.item.dto;

import cucumbermarket.cucumbermarketspring.domain.item.domain.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemCreateRequestDto {
    private Member member;
    private String title;
    private Categories categories;
    private int price;
    private String spec;
    private Address address;
    private Boolean sold;

    @Builder
    public ItemCreateRequestDto(Member member, String title, Categories categories, int price, String spec, Address address, Boolean sold){
        this.member = member;
        this.title = title;
        this.categories = categories;
        this.price = price;
        this.spec = spec;
        this.address = address;
        this.sold = sold;
    }

    public Item toEntity(){
        return Item.builder()
                .member(member)
                .title(title)
                .categories(categories)
                .price(price)
                .spec(spec)
                .address(address)
                .sold(sold)
                .build();
    }
}
