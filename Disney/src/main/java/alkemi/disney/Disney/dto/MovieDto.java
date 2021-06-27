package alkemi.disney.Disney.dto;

import alkemi.disney.Disney.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MovieDto {

    private String title;
    private String image;
    private Date createMovie;

    public static List<MovieDto> transferToCharacterDto(List<Movie> movies){

        List<MovieDto> moviesDtos= new ArrayList<>();
        movies.forEach((mo)->{
            MovieDto tranferDto= new MovieDto();
            tranferDto.setTitle(mo.getTitle());
            tranferDto.setImage(mo.getImage());
            tranferDto.setCreateMovie(mo.getCreateMovie());
            moviesDtos.add(tranferDto);});

        return moviesDtos;
    }
}
