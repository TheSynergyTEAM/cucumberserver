package cucumbermarket.cucumbermarketspring.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Categories {
    DIGIT("디지털/가전"),
    INTERIOR("가구/인테리어"),
    KID("유아동/유아도서"),
    LIVING("생활/가공식품"),
    LEISURE("스포츠/레저"),
    WOMAN("여성의류/잡화"),
    MAN("남성의류/잡화"),
    BEAUTY("뷰티/미용"),
    PET("반려동물용품"),
    HOBBY("게임/취미"),
    CULTURAL("도서/티켓/음반"),
    PLANT("식물"),
    ETC("기타");

    private String value;
}