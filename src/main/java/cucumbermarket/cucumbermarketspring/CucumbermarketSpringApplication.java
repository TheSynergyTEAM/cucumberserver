package cucumbermarket.cucumbermarketspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CucumbermarketSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CucumbermarketSpringApplication.class, args);
	}

}
