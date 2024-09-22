package app.entities;

import app.dtos.DirectorDTO;
import jakarta.persistence.*;
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
@Entity
public class Director {
    @Id
    private long id;
    private String directorName;

    @OneToMany (mappedBy = "director")
    private Set<Movie> movies;

    public Director(DirectorDTO directorDTO) {
        this.id = directorDTO.getId();
        this.directorName = directorDTO.getDirectorName();

    }

}
