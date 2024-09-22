package app.dtos;

import app.entities.Genre;
import app.entities.Movie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenreDTO {
    private long id;
    private String genreName;

    public GenreDTO(Genre genre){
        this.id = genre.getId();
        this.genreName = genre.getGenreName();
    }
}

