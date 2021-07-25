package cucumbermarket.cucumbermarketspring.domain.item.status;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatusService {
    public List<StatusResponseDto> findAll(){
        return Arrays
                .stream(Status.class.getEnumConstants())
                .map(StatusResponseDto::new)
                .collect(Collectors.toList());
    }
}
