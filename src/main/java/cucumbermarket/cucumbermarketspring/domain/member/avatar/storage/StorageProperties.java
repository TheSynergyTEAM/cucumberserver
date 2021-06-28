package cucumbermarket.cucumbermarketspring.domain.member.avatar.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

@ConfigurationProperties("storage")
public class StorageProperties {

    private String location = "images" + File.separator + "avatar";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
