package alkemi.disney.Disney.service;

import alkemi.disney.Disney.exception.CharacterNotFound;
import alkemi.disney.Disney.exception.MovieNotFound;
import alkemi.disney.Disney.exception.RecordNotExistsException;
import alkemi.disney.Disney.exception.UncreatedMovie;
import alkemi.disney.Disney.model.Movie;
import alkemi.disney.Disney.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAll() throws RecordNotExistsException {
        List<Movie> list = movieRepository.findAll();
        return Optional.ofNullable(list).orElseThrow(() -> new RecordNotExistsException("empty movie list"));
    }

    public Movie findByTitle(String name) throws MovieNotFound {
        Movie movie= movieRepository.findByTitle(name);
        return Optional.ofNullable(movie).orElseThrow(() -> new MovieNotFound("there was an error searching movie..."));
    }

    public List<Movie> findByGenreId(Integer genre) throws RecordNotExistsException {
        List<Movie> list = movieRepository.findByGenreId(genre);
        return Optional.ofNullable(list).orElseThrow(() -> new RecordNotExistsException("empty movie list.."));
    }

    public List<Movie> findByOrder(String order,String column) throws RecordNotExistsException {

        Sort sort=Sort.by(Sort.Direction.fromString(order), column);
        List<Movie> list = movieRepository.findAll(sort);
        return Optional.ofNullable(list).orElseThrow(() -> new RecordNotExistsException("empty movie list.."));
    }

    public Movie addCharacter(Movie movie) throws UncreatedMovie {
        Movie m= movieRepository.save(movie);
        return Optional.ofNullable(m).orElseThrow(()-> new UncreatedMovie("there was an error creating movie..."));
    }

    public Movie findById(Integer id) throws MovieNotFound {

        Optional.ofNullable(id).orElseThrow(()->new MovieNotFound("id not present.."));
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.orElseThrow(()-> new MovieNotFound("there was an error searching movie..."));
    }

    public Integer updateCharacter(Movie movie) {
        return movieRepository.updateMovie(movie.getTitle(),movie.getImage(),movie.getCreateMovie(),movie.getQualification(),movie.getId());
    }

    public void deleteMovie(Movie movie) {
        movieRepository.delete(movie);
    }
}
