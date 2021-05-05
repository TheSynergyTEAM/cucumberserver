package cucumbermarket.cucumbermarketspring.domain.item.category;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {
    private String key;
    private String value;

    public CategoryResponseDto(Categories categories){
        this.key = categories.name();
        this.value = categories.getValue();
    }
}
