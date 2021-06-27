package alkemi.disney.Disney.controllerWeb;

import alkemi.disney.Disney.config.RestUtil;
import alkemi.disney.Disney.controller.MovieController;
import alkemi.disney.Disney.dto.CharacterDto;
import alkemi.disney.Disney.dto.MovieDto;
import alkemi.disney.Disney.exception.*;
import alkemi.disney.Disney.model.Character;
import alkemi.disney.Disney.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/movies")
public class MovieControllerWeb {

    private MovieController movieController;

    @Autowired
    public MovieControllerWeb(MovieController movieController) {
        this.movieController = movieController;
    }


    /*7. Listado de Películas
        Deberá mostrar solamente los campos imagen, título y fecha de creación.
        El endpoint deberá ser:
        ● GET /movies*/

    /*10.Búsqueda de Películas o Series
        Deberá permitir buscar por título, y filtrar por género. Además, permitir ordenar los resultados por
        fecha de creación de forma ascendiente o descendiente.
        El término de búsqueda, filtro u ordenación se deberán especificar como parámetros de query:
        ● /movies?name=nombre
        ● /movies?genre=idGenero
        ● /movies?order=ASC | DESC*/
    @GetMapping
    public ResponseEntity<?> allCharacter(@RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "genre", required = false) Integer genre,
                                                @RequestParam(value = "order", required = false) String order) throws RecordNotExistsException, MovieNotFound {

        List<Movie> list = new ArrayList<>();
        ResponseEntity responseEntity = ResponseEntity.ok().build();

        if (!Objects.isNull(name)) {
            Movie m = movieController.findByTile(name);
            return ResponseEntity.ok(m);
        } else if (!Objects.isNull(genre)) {
            list = movieController.findByGenreId(genre);
            responseEntity = ResponseEntity.ok(list);
        } else if (!Objects.isNull(order) && (order.equals("ASC")||order.equals("DESC"))) {
            list = movieController.findByOrder(order,"createMovie");
            responseEntity = ResponseEntity.ok(list);
        } else {
            list = movieController.allMovies();
            responseEntity = ResponseEntity.ok(MovieDto.transferToCharacterDto(list));
        }
        return (list.size() > 0) ? responseEntity : ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    /*8. Detalle de Película / Serie con sus personajes
    Devolverá todos los campos de la película o serie junto a los personajes asociados a la misma*/

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/details")
    public ResponseEntity<List<Movie>> allMoviesDetails() throws RecordNotExistsException {

        List<Movie> list = movieController.allMovies();
        return (list.size() > 0) ? ResponseEntity.ok(list) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /*9. Creación, Edición y Eliminación de Película / Serie
    Deberán existir las operaciones básicas de creación, edición y eliminación de películas o series.
    */

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity createMovie(@RequestBody @Valid Movie movie) throws UncreatedMovie {

        Movie created= movieController.addMovie(movie);
        return ResponseEntity.created(RestUtil.getLocation(created.getId())).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity updateMovie(@RequestBody @Valid Movie movie) throws MovieNotFound {

        Movie movieAux = movieController.findById(movie.getId());
        Integer i= movieController.updateMovie(movie);

        return (i==1)?ResponseEntity.created(RestUtil.getLocation(movie.getId())).build():ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteMovie(@PathVariable Integer id) throws MovieNotFound {

        Movie movie= movieController.findById(id);
        movieController.deleteMovie(movie);
        return ResponseEntity.ok().build();
    }

}




