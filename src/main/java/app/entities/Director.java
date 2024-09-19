package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String directorName;

    @OneToMany (mappedBy = "director")
    private Set<Movie> movies;
}
