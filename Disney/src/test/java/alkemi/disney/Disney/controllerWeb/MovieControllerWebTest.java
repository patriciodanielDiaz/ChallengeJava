package alkemi.disney.Disney.controllerWeb;

import alkemi.disney.Disney.config.RestUtil;
import alkemi.disney.Disney.controller.MovieController;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import alkemi.disney.Disney.dto.MovieDto;
import alkemi.disney.Disney.exception.MovieNotFound;
import alkemi.disney.Disney.exception.RecordNotExistsException;
import alkemi.disney.Disney.exception.UncreatedMovie;
import alkemi.disney.Disney.model.Movie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(PowerMockRunner.class)
@PrepareForTest(RestUtil.class)
public class MovieControllerWebTest {


    MovieControllerWeb movieControllerWeb;
    MovieController movieController;
    Movie movie;
    Movie movie2;
    @Mock
    RestUtil restUtil;

    @Before
    public void setUp() {
        movieController = Mockito.mock(MovieController.class);
        movieControllerWeb = new MovieControllerWeb(movieController);
        PowerMockito.mockStatic(RestUtil.class);
        movie=new Movie(1,"Blancanieves","123456",Date.valueOf(LocalDate.now()),1,null,null,null,null);
        movie2=new Movie(2,"Blancanieves","123456",Date.valueOf(LocalDate.now()),1,null,null,null,null);

    }

    //Get all movies with filter

    @Test
    public void testAllMoviesOk() throws RecordNotExistsException, MovieNotFound {

        List<Movie> list= Arrays.asList(movie,movie2);
        when(movieController.allMovies()).thenReturn(list);

        ResponseEntity re = movieControllerWeb.allCharacter(null,null,null);
        assertEquals(ResponseEntity.ok(MovieDto.transferToCharacterDto(list)), re);

    }

    @Test
    public void testAllMoviesListEmpty() throws RecordNotExistsException, MovieNotFound {

        List<Movie> list= new ArrayList<>();
        when(movieController.allMovies()).thenReturn(list);

        ResponseEntity re = movieControllerWeb.allCharacter(null,null,null);
        assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).build(), re);

    }

    @Test
    public void testAllMoviesByNameOk() throws RecordNotExistsException, MovieNotFound {

        when(movieController.findByTile("Blancanieves")).thenReturn(movie);

        ResponseEntity re = movieControllerWeb.allCharacter ("Blancanieves",null,null);
        assertEquals(ResponseEntity.ok(movie), re);

    }

    @Test
    public void testAllMoviesByGenreOk() throws RecordNotExistsException, MovieNotFound {

        List<Movie> list= Arrays.asList(movie,movie2);
        when(movieController.findByGenreId(1)).thenReturn(list);

        ResponseEntity re = movieControllerWeb.allCharacter(null,1,null);
        assertEquals(ResponseEntity.ok(list), re);

    }

    @Test
    public void testAllMovieByOrderASCOk() throws RecordNotExistsException, MovieNotFound {

        List<Movie> list= Arrays.asList(movie,movie2);
        when(movieController.findByOrder("ASC","createMovie")).thenReturn(list);

        ResponseEntity re = movieControllerWeb.allCharacter(null,null,"ASC");
        assertEquals(ResponseEntity.ok(list), re);

    }
    @Test
    public void testAllMoviesByOrderDESCOk() throws RecordNotExistsException, MovieNotFound {

         List<Movie> list= Arrays.asList(movie2,movie);
        when(movieController.findByOrder("DESC","createMovie")).thenReturn(list);

        ResponseEntity re = movieControllerWeb.allCharacter(null,null,"DESC");
        assertEquals(ResponseEntity.ok(list), re);
    }

    @Test(expected = MovieNotFound.class)
    public void testGetMoviesByNameMovieNotFound() throws MovieNotFound, RecordNotExistsException {

        when(movieController.findByTile("Blancanieves")).thenThrow(new MovieNotFound("not found"));
        movieControllerWeb.allCharacter("Blancanieves",null,null);
        verify(movieController, times(1)).findByTile("Blancanieves");

    }

    @Test(expected = RecordNotExistsException.class)
    public void testGetMoviesByGenreRecordNotExist() throws MovieNotFound, RecordNotExistsException {

        when(movieController.findByGenreId(1)).thenThrow(new RecordNotExistsException("not found"));
        movieControllerWeb.allCharacter(null,1,null);
        verify(movieController, times(1)).findByGenreId(1);

    }

    @Test(expected = RecordNotExistsException.class)
    public void testGetMoviesByOrderRecordNotExist() throws MovieNotFound, RecordNotExistsException {

        when(movieController.findByOrder("ASC","createMovie")).thenThrow(new RecordNotExistsException("not found"));
        movieControllerWeb.allCharacter(null,null,"ASC");
        verify(movieController, times(1)).findByOrder("ASC","createMovie");

    }
    @Test(expected = RecordNotExistsException.class)
    public void testGetAllMoviesRecordNotExist() throws MovieNotFound, RecordNotExistsException {

        when(movieController.allMovies()).thenThrow(new RecordNotExistsException("not found"));
        movieControllerWeb.allCharacter(null,null,null);
        verify(movieController, times(1)).allMovies();

    }

    //Get all Movies Detail

    @Test
    public void testAllMoviesDetailOk() throws RecordNotExistsException {

        List<Movie> list= Arrays.asList(movie,movie2);
        when(movieController.allMovies()).thenReturn(list);

        ResponseEntity re = movieControllerWeb.allMoviesDetails();
        assertEquals(ResponseEntity.ok(list), re);

    }
    @Test
    public void testAllMoviesDetailListEmpty() throws RecordNotExistsException{

        List<Movie> list= new ArrayList<>();
        when(movieController.allMovies()).thenReturn(list);

        ResponseEntity re = movieControllerWeb.allMoviesDetails();
        assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).build(), re);

    }

    @Test(expected = RecordNotExistsException.class)
    public void testAllMoviesDetailRecordNotExist() throws RecordNotExistsException {

        when(movieController.allMovies()).thenThrow(new RecordNotExistsException("not found"));
        movieControllerWeb.allMoviesDetails();
        verify(movieController, times(1)).allMovies();

    }

    //Created movies

    @Test
    public void testCreateMoviesOk() throws UncreatedMovie {

        when(movieController.addMovie(movie)).thenReturn(movie);
        when(RestUtil.getLocation(1)).thenReturn(URI.create("miUri.com"));

        ResponseEntity re = movieControllerWeb.createMovie(movie);
        assertEquals(ResponseEntity.created(RestUtil.getLocation(movie.getId())).build(), re);

    }
    @Test(expected = UncreatedMovie.class)
    public void testCreateMoviesUncreatedMovie() throws UncreatedMovie {

        when(movieController.addMovie(movie)).thenThrow(new UncreatedMovie("not created"));

        movieControllerWeb.createMovie(movie);
        verify(movieController, times(1)).addMovie(movie);

    }

    //Update movie

    @Test
    public void testUpdateMoviesOk() throws MovieNotFound {

        when(movieController.updateMovie(movie)).thenReturn(1);
        when(movieController.findById(movie.getId())).thenReturn(movie);
        when(RestUtil.getLocation(1)).thenReturn(URI.create("miUri.com"));

        ResponseEntity re = movieControllerWeb.updateMovie(movie);
        assertEquals(ResponseEntity.created(RestUtil.getLocation(movie.getId())).build(), re);

    }

    @Test
    public void testUpdateMoviesServerError() throws MovieNotFound {

        when(movieController.findById(movie.getId())).thenReturn(movie);
        when(movieController.updateMovie(movie)).thenReturn(0);

        ResponseEntity re = movieControllerWeb.updateMovie(movie);
        assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).build(), re);

    }

    @Test(expected = MovieNotFound.class)
    public void testUpdateMoviesMovieNotFound() throws MovieNotFound {

        when(movieController.findById(1)).thenThrow(new MovieNotFound("error"));
        movieControllerWeb.updateMovie(movie);
        verify(movieController, times(1)).findById(1);

    }

    //Delete Movie

    @Test
    public void testDeleteMoviesOk() throws MovieNotFound {

        when(movieController.findById(movie.getId())).thenReturn(movie);
        doNothing().when(movieController).deleteMovie(any());
        ResponseEntity re = movieControllerWeb.deleteMovie(movie.getId());
        assertEquals(ResponseEntity.ok().build(), re);

    }

    @Test(expected = MovieNotFound.class)
    public void testDeleteMoviesMovieNotFound() throws MovieNotFound {

        when(movieController.findById(1)).thenThrow(new MovieNotFound("error"));
        movieControllerWeb.deleteMovie(movie.getId());
        verify(movieController, times(1)).findById(1);

    }

}
