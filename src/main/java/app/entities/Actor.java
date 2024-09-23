package app.entities;

import app.dtos.ActorDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Getter
@NoArgsConstructor
@ToString
@Entity
public class Actor {
    @Id
    private long id;
    private String name;

    @ManyToMany
    private Set<Movie> movies;

    public Actor (ActorDTO actorDTO){
        this.id = actorDTO.getId();
        this.name = actorDTO.getName();
    }
}
