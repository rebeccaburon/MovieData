package app.daos;

import app.config.HibernateConfig;
import app.dtos.DirectorDTO;
import app.dtos.MovieDTO;
import app.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class MovieDAO {


    private final EntityManagerFactory emf;

    public MovieDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }





    public MovieDTO create(MovieDTO movieDTO) {
        Movie movie = new Movie(movieDTO);

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            //Check movie in db
            Movie movieFound = em.find(Movie.class, movie.getId());
            if (movieFound != null) {
                System.out.println("Movie exists in database");
                return null;
            }


        }
        return movieDTO;
    }


    public void update(MovieDTO movieDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(movieDTO);
            em.getTransaction().commit();
        }

    }


    public void delete(MovieDTO movieDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(em.merge(movieDTO));
            em.getTransaction().commit();
        }
    }
}
