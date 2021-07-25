package cucumbermarket.cucumbermarketspring.domain.item.status;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonFormat(shape = Shape.OBJECT)
@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public enum Status {
    SOLD("판매완료"),
    RESERVED("예약중"),
    SALE("판매중");

    private final String value;

    private static final Map<String, Status> values =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(Status::getValue, Function.identity())));

    public static Status find(String value){
        return Optional.ofNullable(values.get(value)).orElse(SALE);
    }
}
