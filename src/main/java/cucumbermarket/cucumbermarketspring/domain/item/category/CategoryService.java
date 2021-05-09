package cucumbermarket.cucumbermarketspring.domain.item.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {

    public List<CategoryResponseDto> findAll(){
        return Arrays
                .stream(Categories.class.getEnumConstants())
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }
}
