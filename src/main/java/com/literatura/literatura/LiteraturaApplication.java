package com.literatura.literatura;

import com.literatura.literatura.service.LiteraturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

	@Autowired
	private LiteraturaService service;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) {

		Scanner teclado = new Scanner(System.in);
		int opcion = -1;

		while (opcion != 0) {

			System.out.println("""
					
					===== LITERALURA =====
					
					1 - Buscar libro por título
					2 - Listar libros registrados
					3 - Listar autores registrados
					4 - Listar autores vivos en determinado año
					5 - Listar libros por idioma
					
					0 - Salir
					
					Elige una opción:
					""");

			opcion = teclado.nextInt();
			teclado.nextLine();

			switch (opcion) {

				case 1:
					System.out.println("Escribe el nombre del libro:");
					String nombreLibro = teclado.nextLine();
					service.buscarLibro(nombreLibro);
					break;

				case 2:
					service.listarLibros();
					break;

				case 3:
					service.listarAutores();
					break;

				case 4:
					System.out.println("Ingresa el año:");
					int anio = teclado.nextInt();
					service.autoresVivos(anio);
					break;

				case 5:
					System.out.println("Ingresa el idioma (ej: en, es, fr):");
					String idioma = teclado.nextLine();
					service.librosPorIdioma(idioma);
					break;

				case 0:
					System.out.println("Cerrando aplicación...");
					break;

				default:
					System.out.println("Opción inválida");
			}
		}
	}
}
