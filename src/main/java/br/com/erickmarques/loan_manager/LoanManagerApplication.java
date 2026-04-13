package br.com.erickmarques.loan_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LoanManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanManagerApplication.class, args);
	}

}
