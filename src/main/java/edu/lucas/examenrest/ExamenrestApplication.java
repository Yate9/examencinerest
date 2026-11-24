package edu.lucas.examenrest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExamenrestApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ExamenrestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Programa iniciado");
	}

}
