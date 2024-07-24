package shop.project.venver_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class VenverUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(VenverUserApplication.class, args);
    }

}
