package cucumbermarket.cucumbermarketspring.domain.member.address;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class AddressService {

//    private RestTemplate restTemplate;
//    private final String requestUrl = "https://sgisapi.kostat.go.kr/OpenAPI3/auth/authentication.json";
//    private final String cityRequestUrl = "https://sgisapi.kostat.go.kr/OpenAPI3/addr/stage.json";
//    private final String consumer_key = "72a176d718cc486a8cef";
//    private final String consumer_secret = "88a1aec13d6b4c4d9f98";
//    private final String authUrl = requestUrl+"?consumer_key="+consumer_key+"&consumer_secret="+consumer_secret;
//    private String access_token;
    AddressVO addressVO = new AddressVO();

    public List<CityDto> requestCity(){
        HashMap<Integer, String> cityMap = addressVO.getCityMap();
        Set<Integer> keySet = cityMap.keySet();
        List<CityDto> cityDtos = new ArrayList<>();
        for (Integer s : keySet) {
            String city = cityMap.get(s);
            cityDtos.add(new CityDto(s, city));
        }
        return cityDtos;
    }

    public StreetDto requestStreet(Integer id) {

        HashMap<String, ArrayList<String>> streetMap = addressVO.getStreetMap();
        HashMap<Integer, String> cityMap = addressVO.getCityMap();
        String city = cityMap.get(id);
        ArrayList<String> streets = streetMap.get(city);
        StreetDto streetDto = new StreetDto(city, streets);
        return streetDto;
    }


    @Data
    static class CityDto {
        int id;
        String name;
        public CityDto(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
    }
    @Data
    static class StreetDto {
        String city;
        List<String> streetList = new ArrayList<>();
        public StreetDto(String city, List<String> streetList) {
            this.city = city;
            this.streetList = streetList;
        }
    }

}
