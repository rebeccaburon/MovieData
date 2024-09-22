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
    private long id;
    private String title;
    private String mediaType;
    private String overview;
    private LocalDate releaseDate;
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
        this.mediaType = movieDTO.getMediaType();
        this.overview = movieDTO.getOverview();
        this.releaseDate = movieDTO.getReleaseDate();
        this.originalLanguage = movieDTO.getOriginalLanguage();
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

