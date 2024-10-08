package app.dtos;

import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO {

    private long id;
    @JsonProperty ("original_title")
    private String title;
    @JsonProperty ("overview")
    private String overview;
    @JsonProperty ("release_date")
    private LocalDate releaseDate;
    @JsonProperty ("original_language")
    private String originalLanguage;
    @JsonProperty("genres")
    private Set<GenreDTO> genres;
    private Set<ActorDTO> actors;
    private DirectorDTO director;


    public MovieDTO (Movie movie){
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.overview = movie.getOverview();
        this.releaseDate = movie.getReleaseDate();
        this.originalLanguage = movie.getOriginalLanguage();

        this.genres = movie.getGenres().stream()
                .map(genre -> new GenreDTO(genre))
                .collect(Collectors.toSet());

        this.actors = movie.getActors().stream()
                .map(actor -> new ActorDTO(actor))
                .collect(Collectors.toSet());

        this.director = movie.getDirector() !=null ? new DirectorDTO(movie.getDirector()) : null;

    }
}
