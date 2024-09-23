package app.dtos;

import app.entities.Actor;
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
public class ActorDTO {
    private long id;
    private String name;


   public ActorDTO(Actor actor){
       this.id = actor.getId();
       this.name = actor.getName();
   }
}
