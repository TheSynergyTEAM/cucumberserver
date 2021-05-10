package cucumbermarket.cucumbermarketspring.domain.chat.Message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageStatus {
    RECEIVED, DELIVERED
}
