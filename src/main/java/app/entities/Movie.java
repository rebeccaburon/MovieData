package app.entities;

import app.dtos.MovieDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    private String title;
    private String overview;
    @Column(name ="release_date")
    private LocalDate releaseDate;
    @Column (name = "original_language")
    private String originalLanguage;

    @ManyToMany
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany
    private Set<Actor> actors = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    public Movie(MovieDTO movieDTO) {
        this.id = movieDTO.getId();
        this.title = movieDTO.getTitle();
        this.overview = movieDTO.getOverview();
        this.releaseDate = movieDTO.getReleaseDate();
        this.originalLanguage = movieDTO.getOriginalLanguage();
        this.director = movieDTO.getDirector() == null ? null : new Director(movieDTO.getDirector());
    }

    public void addGenre(Genre genre) {
        if (genre !=null){
            genres.add(genre);
        }
    }
    public void addActor(Actor actor) {
        if (actor !=null){
            actors.add(actor);
        }
    }
}

