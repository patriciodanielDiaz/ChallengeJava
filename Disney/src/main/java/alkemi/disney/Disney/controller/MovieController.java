package alkemi.disney.Disney.controller;

import alkemi.disney.Disney.exception.MovieNotFound;
import alkemi.disney.Disney.exception.RecordNotExistsException;
import alkemi.disney.Disney.exception.UncreatedMovie;
import alkemi.disney.Disney.model.Character;
import alkemi.disney.Disney.model.Movie;
import alkemi.disney.Disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    
    public List<Movie> allMovies() throws RecordNotExistsException {
        List<Movie> list = movieService.getAll();
        return list;
    }

    public Movie findByTile(String name) throws MovieNotFound {
        Movie movie= movieService.findByTitle(name);
        return movie;
        
    }

    public List<Movie> findByGenreId(Integer genre) throws RecordNotExistsException {
        List<Movie> list= movieService.findByGenreId(genre);
        return list;
    }

    public List<Movie> findByOrder(String order,String column) throws RecordNotExistsException {
        List<Movie> list= movieService.findByOrder(order,column);
        return list;
    }

    public Movie addMovie(Movie movie) throws UncreatedMovie {
        Movie created = movieService.addCharacter(movie);
        return created;
    }

    public Movie findById(Integer id) throws MovieNotFound {
        Movie movie= movieService.findById(id);
        return movie;
    }

    public Integer updateMovie(Movie movie) {
        Integer c= movieService.updateCharacter(movie);
        return c;
    }

    public void deleteMovie(Movie movie) {
        movieService.deleteMovie(movie);
    }
}
