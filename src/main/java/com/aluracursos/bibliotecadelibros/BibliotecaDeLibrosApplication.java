package com.aluracursos.bibliotecadelibros;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaDeLibrosApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaDeLibrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hola Mundo");
	}
}
