package cucumbermarket.cucumbermarketspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CucumbermarketSpringApplication extends SpringBootServletInitializer {
	public CucumbermarketSpringApplication() {
		super();
		setRegisterErrorPageFilter(false);
	}

	public static void main(String[] args) {
		SpringApplication.run(CucumbermarketSpringApplication.class, args);
	}

}
