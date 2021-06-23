package cucumbermarket.cucumbermarketspring.domain.item.dto;

import lombok.Getter;

@Getter
public class ItemSoldDto {
    private Long itemId;
    private Long sellerId;
    private Long buyerId;
}
