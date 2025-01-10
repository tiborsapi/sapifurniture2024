package ro.sapientia.furniture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("door")
public class DoorApplication{
	public static void main(String[] args) {
		SpringApplication.run(DoorApplication.class, args);
	}
}