package br.csi.politecnico.financecontrol;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "API de controle de finanças",
				version = "1.0",
				description = "Api para controlar as finanças",
				contact = @Contact(name = "Gue10", email = "joaoguedes1404@gmail.com")
		)
)
@SpringBootApplication
public class FinanceControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceControlApplication.class, args);
	}

}
