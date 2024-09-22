package app.daos;

import app.config.HibernateConfig;
import app.dtos.ActorDTO;
import app.dtos.DirectorDTO;
import app.dtos.GenreDTO;
import app.dtos.MovieDTO;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;
import jakarta.persistence.*;
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


    public List<MovieDTO> getMovies() {
        try (EntityManager em = emf.createEntityManager()) {
            List<MovieDTO> found = em.createQuery("SELECT new app.dtos.MovieDTO (m) FROM Movie m", MovieDTO.class).getResultList();
            if (found.isEmpty()) {
                throw new NoResultException("No movies found");
            }
            return found;
        }
    }

    public void create(MovieDTO movieDTO) {
        Movie movie = new Movie(movieDTO);

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            //Check movie in db
            Movie movieFound = em.find(Movie.class, movie.getId());
            if (movieFound != null) {
                System.out.println("Movie exists in database");
                return;
            }
            //Add genre
            if (movieDTO.getGenres() != null) {

                for (GenreDTO genres : movieDTO.getGenres()) {
                    Genre genre = new Genre(genres);
                    movie.addGenre(genre);

                    //Check genre in db, if not persist.
                    Genre genreFound = em.find(Genre.class, genres.getId());
                    if (genreFound == null) {
                        em.persist(genre);
                    }
                }
            }
            //Add actor
            if (movieDTO.getActors() != null) {
                for (ActorDTO actors : movieDTO.getActors()) {
                    Actor actor = new Actor(actors);
                    movie.addActor(actor);

                    //Check actor in db, if not persist.
                    Actor actorFound = em.find(Actor.class, actors.getId());
                    if (actorFound == null) {
                        em.persist(actor);
                    }

                }
            }
            //Add Director
            if (movieDTO.getDirector() != null) {
                Director director = new Director(movieDTO.getDirector());
                movie.setDirector(director);

                //Check director in db, if not persist.
                Director directorFound = em.find(Director.class, director.getId());
                if (directorFound == null) {
                    em.persist(director);
                }
            }
            em.persist(movie);
            em.getTransaction().commit();
        }
    }


    public MovieDTO update(MovieDTO movieDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            //Check movie exist
            Movie movieFound = em.find(Movie.class, movieDTO.getId());
            if (movieFound == null) {
                throw new EntityNotFoundException();
            }

            //Update movie
            Movie movie = new Movie(movieDTO);
            if (movie.getTitle() != null) {
                movie.setTitle(movieDTO.getTitle());
                movie.setMediaType(movieDTO.getMediaType());
                movie.setOverview(movieDTO.getOverview());
                movie.setReleaseDate(movieDTO.getReleaseDate());
            }

            //Genre update
            Set<GenreDTO> genreDTOS = movieDTO.getGenres();
            for (GenreDTO genres : genreDTOS) {
                Genre genreFound = em.find(Genre.class, genres.getId());

                if (genreFound != null) {
                    movie.addGenre(genreFound);
                } else {
                    Genre addingGenre = new Genre(genres);
                    em.persist(addingGenre);
                    movie.addGenre(addingGenre);
                }
            }
            //Director update
            DirectorDTO directorDTO = movieDTO.getDirector();
            Director directorFound = em.find(Director.class, directorDTO.getId());
            if (directorFound != null) {
                movie.setDirector(directorFound);
            } else {
                Director addingDirector = new Director(directorDTO);
                em.persist(addingDirector);
                movie.setDirector(addingDirector);
            }

            //Actor update
            Set<ActorDTO> actorDTOS = movieDTO.getActors();
            for (ActorDTO actors : actorDTOS) {
                Actor actorFound = em.find(Actor.class, actors.getId());
                if (actorFound != null) {
                    movie.addActor(actorFound);
                } else {
                    Actor addingActor = new Actor(actors);
                    em.persist(addingActor);
                    movie.addActor(addingActor);
                }
            }

            em.merge(movie);
            em.getTransaction().commit();
            return new MovieDTO(movie);
        } catch (Exception e) {
            throw new RuntimeException("Updating faild", e);
        }

    }


    public void delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Movie found = em.find(Movie.class, id);
            if (found == null) {
                throw new EntityNotFoundException();
            }
            em.getTransaction().begin();
            em.remove(em.merge(found));
            em.getTransaction().commit();
        }
    }
}
