package cucumbermarket.cucumbermarketspring.domain.member.address;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@ResponseBody
@RestController
@RequiredArgsConstructor
public class AddressRequestController {
    private final AddressService addressService;

    @CrossOrigin
    @GetMapping("/address/city")
    public List<AddressService.CityDto> getCity() {
        List<AddressService.CityDto> cityDtos = addressService.requestCity();
        return cityDtos;
    }

    @CrossOrigin
    @GetMapping("/address/city/{id}")
    public AddressService.StreetDto getStreet(@PathVariable Integer id) {
        AddressService.StreetDto streetDtos = addressService.requestStreet(id);
        return streetDtos;
    }
}
