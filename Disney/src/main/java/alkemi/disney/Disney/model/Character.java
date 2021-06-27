package alkemi.disney.Disney.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "characters")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String image;

    @NotNull
    private String name;

    @NotNull
    private Integer Age;

    @NotNull
    private Float weight;

    @NotNull
    private String story;

    @ManyToMany
    @JoinTable(name="movies_by_characters", joinColumns={@JoinColumn(name="character_id")}, inverseJoinColumns={@JoinColumn(name="movie_id")})
    @JsonIgnoreProperties(value = "characters")
    private List<Movie> movies;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "update_at")
    private String updateAt;

}
