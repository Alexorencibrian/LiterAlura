package com.alura.literaalura;

import com.alura.literaalura.client.BookClient;
import com.alura.literaalura.model.Author;
import com.alura.literaalura.model.Book;
import com.alura.literaalura.repository.BookRepository;
import com.alura.literaalura.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraAluraApplication implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(LiteraAluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menú:");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (opcion == 1) {
                System.out.print("Ingrese el título del libro: ");
                String title = scanner.nextLine();
                BookClient bookClient = new BookClient();
                String jsonResponse = bookClient.getBooksByTitle(title);
                JsonParser parser = new JsonParser();
                List<Book> books = parser.parseBooks(jsonResponse);
                for (Book book : books) {
                    bookRepository.save(book);
                    System.out.println(book);
                }
            } else if (opcion == 2) {
                break;
            } else {
                System.out.println("Opción no válida.");
            }
        }
        scanner.close();
    }
}
