package ro.sapientia.furniture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ro.sapientia")
public class FurnitureApplication {

	public static void main(String[] args) {
		SpringApplication.run(FurnitureApplication.class, args);
	}

}
