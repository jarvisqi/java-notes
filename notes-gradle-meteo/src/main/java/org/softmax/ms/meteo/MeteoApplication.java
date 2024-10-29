package org.softmax.ms.meteo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.softmax.ms.meteo.*")
public class MeteoApplication {

    public static void main(String[] args) {

        SpringApplication.run(MeteoApplication.class, args);
    }

}
