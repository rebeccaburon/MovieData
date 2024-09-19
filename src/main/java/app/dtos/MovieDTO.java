package app.dtos;

import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MovieDTO {

    private long id;
    private String title;
    private String mediaType;
    private String overview;
    private LocalDate releaseDate;
    private String originalLanguage;
    private Set<Genre> genres;
    private Set<Actor> actors;
    private Director director;

}
