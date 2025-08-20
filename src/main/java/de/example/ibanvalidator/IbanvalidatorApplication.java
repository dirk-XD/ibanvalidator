package de.example.ibanvalidator;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "IBAN validation", version = "1.0"))
public class IbanvalidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbanvalidatorApplication.class, args);
	}

}
