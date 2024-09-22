package app.dtos;

import app.entities.Director;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectorDTO {
    private long id;
    private String directorName;
    public DirectorDTO (Director director){
        this.id = director.getId();
        this.directorName = director.getDirectorName();
    }

}
