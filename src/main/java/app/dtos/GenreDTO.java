package app.dtos;

import app.entities.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenreDTO {
    private long id;
    private String name;

    public GenreDTO(Genre genre){
        this.id = genre.getId();
        this.name = genre.getName();
    }
}

