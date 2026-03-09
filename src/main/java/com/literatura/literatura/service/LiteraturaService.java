package com.literatura.literatura.service;

import com.literatura.literatura.api.ConsumoAPI;
import com.literatura.literatura.api.ConvierteDatos;
import com.literatura.literatura.dto.DatosLibro;
import com.literatura.literatura.dto.DatosRespuesta;
import com.literatura.literatura.model.Autor;
import com.literatura.literatura.model.Libro;
import com.literatura.literatura.repository.AutorRepository;
import com.literatura.literatura.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiteraturaService {

    private ConsumoAPI consumo = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    public LiteraturaService(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void buscarLibro(String nombreLibro) {

        String url = "https://gutendex.com/books/?search=" + nombreLibro.replace(" ", "%20");

        String json = consumo.obtenerDatos(url);
        DatosRespuesta datos = conversor.obtenerDatos(json, DatosRespuesta.class);

        if (datos.results().isEmpty()) {
            System.out.println("Libro no encontrado");
            return;
        }

        DatosLibro datosLibro = datos.results().get(0);

        String nombreAutor = datosLibro.authors().get(0).name();

        Autor autor = new Autor(nombreAutor);
        autorRepository.save(autor);

        Libro libro = new Libro(
                datosLibro.title(),
                datosLibro.languages().get(0),
                datosLibro.download_count(),
                autor
        );

        libroRepository.save(libro);

        System.out.println("Libro guardado en la base de datos:");
        System.out.println(datosLibro.title());
    }

    public void listarLibros() {

        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        libros.forEach(libro ->
                System.out.println(libro.getTitulo())
        );
    }

    public void listarAutores() {

        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        autores.forEach(autor ->
                System.out.println(autor.getNombre())
        );
    }

    public void autoresVivos(int anio) {

        List<Autor> autores = autorRepository.findAll();

        autores.forEach(autor ->
                System.out.println(autor.getNombre())
        );
    }

    public void librosPorIdioma(String idioma) {

        List<Libro> libros = libroRepository.findAll();

        libros.stream()
                .filter(libro -> libro.getIdioma().equalsIgnoreCase(idioma))
                .forEach(libro ->
                        System.out.println(libro.getTitulo())
                );
    }
}
