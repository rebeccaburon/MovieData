package app.daos;

import app.config.HibernateConfig;
import app.dtos.MovieDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieDAO implements IDAO<MovieDTO> {


    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("movie_data");


    @Override
    public MovieDTO getId(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(MovieDTO.class, id);
        }
    }

    @Override
    public Set<MovieDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<MovieDTO> query = em.createQuery("SELECT m FROM Movie m", MovieDTO.class);
            List<MovieDTO> movieDTOList = query.getResultList();
            return movieDTOList.stream()
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public void create(MovieDTO movieDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(movieDTO);
            em.getTransaction().commit();
        }
    }

    @Override
    public void update(MovieDTO movieDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(movieDTO);
            em.getTransaction().commit();
        }

    }

    @Override
    public void delete(MovieDTO movieDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(em.merge(movieDTO));
            em.getTransaction().commit();
        }
    }
}
