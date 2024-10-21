package com.mantisa.inventory;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MantisaInventoryApplication {

    public static void main(String[] args) {
        // Cargar las variables de entorno desde el archivo .env
        Dotenv dotenv = Dotenv.load();

        // Se pueden usar las variables, pero no es necesario si no se usan
        String host = dotenv.get("SPRING_DATASOURCE_HOST");
        String port = dotenv.get("SPRING_DATASOURCE_PORT");

        System.out.println("Database host: " + host);
        System.out.println("Database port: " + port);

        SpringApplication.run(MantisaInventoryApplication.class, args);
    }
}
