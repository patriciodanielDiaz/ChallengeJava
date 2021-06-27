package alkemi.disney.Disney.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String title;

    @NotNull
    private String image;

    @NotNull
    @Column(name = "creation_movie")
    private Date createMovie;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer qualification;

    @ManyToMany
    @JoinTable(name="movies_by_characters", joinColumns={@JoinColumn(name="movie_id")}, inverseJoinColumns={@JoinColumn(name="character_id")})
    @JsonIgnoreProperties(value = "movies")
    private List<Character> characters;

    @ManyToMany
    @JoinTable(name="genre_by_movie", joinColumns={@JoinColumn(name="movie_id")}, inverseJoinColumns={@JoinColumn(name="genre_id")})
    @JsonIgnoreProperties(value = "movies")
    private List<Genre> genres;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "update_at")
    private String updateAt;

}


//SELECT ch.* ,mo.title FROM characters ch inner join movies_by_characters mbch on ch.id=mbch.character_id inner join movies mo on mbch.movie_id=mo.id