package cucumbermarket.cucumbermarketspring.domain.item.status;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @GetMapping("/status")
    private List<StatusResponseDto> findAll(){
        return statusService.findAll();
    }

}
