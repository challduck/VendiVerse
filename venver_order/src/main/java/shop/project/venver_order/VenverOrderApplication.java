package shop.project.venver_order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class VenverOrderApplication {
    public static void main(String[] args) { SpringApplication.run(VenverOrderApplication.class, args); }
}
