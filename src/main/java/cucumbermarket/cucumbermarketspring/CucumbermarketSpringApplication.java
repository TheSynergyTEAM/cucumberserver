package cucumbermarket.cucumbermarketspring;

import cucumbermarket.cucumbermarketspring.domain.member.avatar.storage.StorageProperties;
import cucumbermarket.cucumbermarketspring.domain.member.avatar.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class CucumbermarketSpringApplication extends SpringBootServletInitializer {
	public CucumbermarketSpringApplication() {
		super();
		setRegisterErrorPageFilter(false);
	}

	public static void main(String[] args) {
		SpringApplication.run(CucumbermarketSpringApplication.class, args);
	}

	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
		return new HiddenHttpMethodFilter();
	}

//	@Bean
//	CommandLineRunner init(StorageService storageService) {
//		return (args) -> {
//			storageService.deleteAll();
//			storageService.init();
//		};
//	}
}
