package motion.hash;

import motion.hash.service.HashingSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HashApplication {

	public static void main(String[] args) {
		SpringApplication.run(HashApplication.class, args);
	}
}
