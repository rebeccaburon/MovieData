package app.entities;

import jakarta.persistence.*;
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
@Entity
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String mediaType;
    private String overview;
    private LocalDate releaseDate;
    private String originalLanguage;
    @ManyToMany
    private Set<Genre> genres;
    @ManyToMany
    private Set<Actor> actors;
    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;


}
