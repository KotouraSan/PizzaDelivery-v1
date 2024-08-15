package uz.ksan.backend.pizzadelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PizzaDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzaDeliveryApplication.class, args);
    }

}
