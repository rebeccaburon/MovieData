package app;

import app.config.HibernateConfig;
import app.daos.MovieDAO;
import app.dtos.ActorDTO;
import app.dtos.DirectorDTO;
import app.dtos.GenreDTO;
import app.dtos.MovieDTO;
import app.services.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("movie_data");
        MovieDAO movieDAO = new MovieDAO(emf);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        MovieService movieService = new MovieService(objectMapper);

        List<MovieDTO> movieDTOList = movieService.getDanishMoviesFromLastFiveYears("results","https://api.themoviedb.org/3/discover/movie?with_origin_country=DK" );
        for (MovieDTO m : movieDTOList) {
            movieDAO.create(m);
        }
        System.out.println(movieDTOList);

    }
}
