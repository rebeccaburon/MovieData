package app.dtos;

import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MovieDTO {

    private long id;
    @JsonProperty ("original_title")
    private String title;
    @JsonProperty ("media_type")
    private String mediaType;
    @JsonProperty ("overview")
    private String overview;
    @JsonProperty ("release_date")
    private LocalDate releaseDate;
    @JsonProperty ("original_language")
    private String originalLanguage;
    @JsonProperty ("genre_ids")
    private Set<GenreDTO> genres;
    private Set<ActorDTO> actors;
    private DirectorDTO director;


    public MovieDTO (Movie movie){
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.mediaType = movie.getMediaType();
        this.overview = movie.getOverview();
        this.releaseDate = movie.getReleaseDate();
        this.originalLanguage = movie.getOriginalLanguage();
        this.genres = new HashSet<>();
        this.actors = new HashSet<>();
        this.director = movie.getDirector() !=null ? new DirectorDTO(movie.getDirector()) : null;

    }
}
