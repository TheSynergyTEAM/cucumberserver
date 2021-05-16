package cucumbermarket.cucumbermarketspring.domain.member;

import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import cucumbermarket.cucumbermarketspring.domain.member.address.AddressVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initDB();
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final String[] randomNames = {
                "곽형수", "전창희", "박진석", "이상지", "송성훈", "김혜석", "김장군", "최정웅", "서재균", "고상희", "이승호",
                "이세연", "홍승현", "최주원", "변지경", "최은아", "이민서", "권주안", "진하은", "김소경", "임수영", "정유진"
        };
        private List<String> randomEmails = new ArrayList<String>();

        private final EntityManager em;

        private AddressVO addressVO = new AddressVO();
        private int cityCount = addressVO.getCityMapLength();


        public void initDB() {
            for (int i = 1; i < randomNames.length+1; i++) {
                randomEmails.add("example" + i + "@gmail.com");
            }
            for (int i = 1; i < randomNames.length+1; i++) {
                memberCreate(i);
            }

        }

        private void memberCreate(int i) {
            HashMap<Integer, String> cityMap = addressVO.getCityMap();
            HashMap<String, ArrayList<String>> streetMap = addressVO.getStreetMap();
            String city;
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            if(i > cityCount){
                city = cityMap.get(i % cityCount);
            }
            else{
                city = cityMap.get(i);
            }
            String street = streetMap.get(city).get(0);
            Address address = getAddress(
                    city,
                    street,
                    "OOO로 XXX",
                    "00000"
            );

            Member member = getMember(
                    randomNames[i-1],
                    bCryptPasswordEncoder.encode("1234"),
                    address,
                    LocalDate.of(1992, 7, 8),
                    randomEmails.get(i-1),
                    "010-1111-1111",
                    0
            );
            em.persist(member);
        }

        private Member getMember(String name, String password, Address address1, LocalDate birthdate, String email, String contact, Integer ratingScore) {
            return new Member(
                    name,
                    password,
                    address1,
                    birthdate,
                    email,
                    contact,
                    ratingScore,
                    "USER"
            );
        }

        private Address getAddress(String city, String street1, String street2, String zipcode) {
            return new Address(
                    city,
                    street1,
                    street2,
                    zipcode
            );
        }
    }
}
