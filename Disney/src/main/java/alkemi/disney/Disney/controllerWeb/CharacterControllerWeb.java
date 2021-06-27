package alkemi.disney.Disney.controllerWeb;

import alkemi.disney.Disney.config.RestUtil;
import alkemi.disney.Disney.controller.CharacterController;
import alkemi.disney.Disney.dto.CharacterDto;
import alkemi.disney.Disney.exception.CharacterNotFound;
import alkemi.disney.Disney.exception.CharacterNotUpdated;
import alkemi.disney.Disney.exception.RecordNotExistsException;
import alkemi.disney.Disney.exception.UncreatedCharacter;
import alkemi.disney.Disney.model.Character;
import alkemi.disney.Disney.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("api/characters")
public class CharacterControllerWeb {

    private CharacterController characterController;

    @Autowired
    public CharacterControllerWeb(CharacterController characterController) {
        this.characterController = characterController;
    }

    /* 3. Listado de Personajes
         El listado deberá mostrar:
            ● Imagen.
            ● Nombre.
         El endpoint deberá ser:
            ● /characters

       6. Búsqueda de Personajes
    Deberá permitir buscar por nombre, y filtrar por edad, peso o películas/series en las que participó.
    Para especificar el término de búsqueda o filtros se deberán enviar como parámetros de query:
            ● GET /characters?name=nombre
            ● GET /characters?age=edad
            ● GET /characters?movies=idMovie*/


    @GetMapping
    public ResponseEntity<?> allCharacterByName(@RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "age", required = false) Integer age,
                                                @RequestParam(value = "movies", required = false) Integer idMovie) throws RecordNotExistsException, CharacterNotFound {

        List<Character> list= new ArrayList<>();
        ResponseEntity responseEntity;

        if(!Objects.isNull(name)){
            Character c = characterController.findByName(name);
            return ResponseEntity.ok(c);
        }
        else if (!Objects.isNull(age)) {
            list= characterController.findByAge(age);
            responseEntity=ResponseEntity.ok(list);
        }
        else if (!Objects.isNull(idMovie)) {
            list= characterController.findByMovieId(idMovie);
            responseEntity=ResponseEntity.ok(list);
        }
        else {
            list = characterController.allCharacter();
            responseEntity=ResponseEntity.ok(CharacterDto.transferToCharacterDto(list));
        }
        return (list.size() > 0) ? responseEntity : ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


    /*4. Creación, Edición y Eliminación de Personajes (CRUD)
         Deberán existir las operaciones básicas de creación, edición y eliminación de personajes.*/

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity createCharacter(@RequestBody @Valid Character character) throws UncreatedCharacter {

        Character created= characterController.addCharacter(character);
        return ResponseEntity.created(RestUtil.getLocation(created.getId())).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity updateCharacter(@RequestBody @Valid Character character) throws CharacterNotFound {

        Character characterAux = characterController.findById(character.getId());
        Integer i= characterController.updateCharacter(character);

        return (i==1)?ResponseEntity.created(RestUtil.getLocation(character.getId())).build():ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCharacter(@PathVariable Integer id) throws CharacterNotFound {

        Character character= characterController.findById(id);
        characterController.deleteCharacter(character);
        return ResponseEntity.ok().build();
    }

    /*5. Detalle de Personaje
    En el detalle deberán listarse todos los atributos del personaje, como así también sus películas o
    series relacionadas.*/
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/details")
    public ResponseEntity<List<Character>> allCharacterDetails() throws RecordNotExistsException {

        List<Character> list = characterController.allCharacter();
        return (list.size() > 0) ? ResponseEntity.ok(list) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    /*@GetMapping("/characters")
    public ResponseEntity<List<CharacterDto>> allCharacter() throws RecordNotExistsException {

        List<Character> list = characterController.allCharacter();
        return (list.size() > 0) ? ResponseEntity.ok(CharacterDto.transferToCharacterDto(list)) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }*/

    /*@GetMapping("/characters")
    public ResponseEntity<Character> CharacterByName(@RequestParam(value = "name", required = true) String name) throws CharacterNotFound {

        Character character= characterController.findByName(name);
        return (character!= null) ? ResponseEntity.ok(character) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
    @GetMapping("/characters")
    public ResponseEntity<List<Character>> allCharacterByAge(@RequestParam(value = "age", required = true) Integer age) throws RecordNotExistsException {

        List<Character> list= new ArrayList<>();
        list= characterController.findByAge(age);
        return  (list.size() > 0) ? ResponseEntity.ok(list) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
    @GetMapping("/characters")
    public ResponseEntity<List<Character>> allCharacterByMovies(@RequestParam(value = "movies", required = true) Integer idMovie) throws RecordNotExistsException {

        List<Character> list= new ArrayList<>();
        list= characterController.findByMovieId(idMovie);
        return  (list.size() > 0) ? ResponseEntity.ok(list) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }*/


     /*{

        "image": "77777777777777",
        "name": "enanito 7",
        "weight": 765.5,
        "story": "sergretretreregrg regr grr erg ",
        "movies":[{"id":1}],
        "age": 95
    }*/


}
