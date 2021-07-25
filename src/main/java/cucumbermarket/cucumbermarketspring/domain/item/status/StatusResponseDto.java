package cucumbermarket.cucumbermarketspring.domain.item.status;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatusResponseDto {
    private String key;
    private String value;

    public StatusResponseDto(Status status){
        this.key = status.name();
        this.value = status.getValue();
    }
}
