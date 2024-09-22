package app.dtos;

import app.entities.Actor;
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
public class ActorDTO {
    private long id;
    private String actorName;


    public ActorDTO(Actor actor){
        this.id = actor.getId();
        this.actorName = actor.getActorName();
    }
}
