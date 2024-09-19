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
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String actorName;
    @ManyToMany
    private Set<Movie> movies;
}
