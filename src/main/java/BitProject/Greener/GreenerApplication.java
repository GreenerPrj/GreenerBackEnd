package BitProject.Greener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GreenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenerApplication.class, args);
	}

}
