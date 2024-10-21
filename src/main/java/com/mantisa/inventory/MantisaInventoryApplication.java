package com.mantisa.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class MantisaInventoryApplication {

    public static void main(String[] args) {
        System.out.println("SPRING_PROFILES_ACTIVE: " + System.getenv("SPRING_PROFILES_ACTIVE"));
        System.out.println("SPRING_DATASOURCE_HOST: " + System.getenv("SPRING_DATASOURCE_HOST"));
        System.out.println("SPRING_DATASOURCE_PORT: " + System.getenv("SPRING_DATASOURCE_PORT"));
        System.out.println("SPRING_DATASOURCE_USERNAME: " + System.getenv("SPRING_DATASOURCE_USERNAME"));
        System.out.println("SPRING_DATASOURCE_PASSWORD: " + System.getenv("SPRING_DATASOURCE_PASSWORD"));


        String profile = System.getenv("SPRING_PROFILES_ACTIVE");
        if (profile != null && !profile.isEmpty()) {
            System.out.println("Profile active: " + profile);
            System.setProperty("spring.profiles.active", profile);
        } else {
            System.out.println("No profile active, using default.");
        }

        SpringApplication.run(MantisaInventoryApplication.class, args);
    }

}
