package app.daos;

import app.config.HibernateConfig;
import app.dtos.MovieDTO;
import app.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieDAOTest {
    private static EntityManagerFactory emf;
    private static MovieDAO movieDAO;

    MovieDTO move1, move2, move3;

    @BeforeAll
    static void setUpALL() {
        HibernateConfig.getEntityManagerFactoryForTest();
        emf = HibernateConfig.getEntityManagerFactory("movie_data");
        movieDAO = new MovieDAO(emf);
    }

    @AfterAll
    static  void tearDownAll(){
        emf.close();
    }

    @BeforeEach
    void setUp(){
        EntityManager em = emf.createEntityManager();

        move1 = new MovieDTO();
        move2 = new MovieDTO();
        move3 = new MovieDTO();
        em.getTransaction().begin();
        em.persist(move1);
        em.persist(move2);
        em.persist(move3);
        em.getTransaction().commit();
    }
    @Test
    void getId() {
    }

    @Test
    void getAll() {
    }

    @Test
    void create() {
        MovieDTO actualMovie = new MovieDTO();

        MovieDTO expectedMovie = movieDAO.create(actualMovie);

        assertNotNull(expectedMovie);
        assertEquals(expectedMovie, actualMovie);

    }

    @Test
    void update() {
    }
}