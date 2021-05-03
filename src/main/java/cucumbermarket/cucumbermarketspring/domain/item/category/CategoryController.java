package cucumbermarket.cucumbermarketspring.domain.item.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/category")
    private List<CategoryResponseDto> findAll(){
        return categoryService.findAll();
    }
}
