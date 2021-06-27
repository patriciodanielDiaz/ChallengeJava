package alkemi.disney.Disney.controller;

import alkemi.disney.Disney.exception.CharacterNotFound;
import alkemi.disney.Disney.exception.RecordNotExistsException;
import alkemi.disney.Disney.exception.UncreatedCharacter;
import alkemi.disney.Disney.model.Character;
import alkemi.disney.Disney.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.util.List;

@Controller
public class CharacterController {

    private CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    public List<Character> allCharacter() throws RecordNotExistsException {
        List<Character> list = characterService.getAll();
        return list;
    }

    public Character addCharacter(Character character) throws UncreatedCharacter {
        Character created = characterService.addCharacter(character);
        return created;
    }

    public Character findById(Integer id) throws CharacterNotFound {
        Character character= characterService.findById(id);
        return character;
    }

    public Integer updateCharacter(Character character) {
        Integer c= characterService.updateCharacter(character);
        return c;
    }

    public void deleteCharacter(Character character){
        characterService.deleteCharacter(character);
    }

    public Character findByName(String name) throws CharacterNotFound {
        Character character= characterService.findByName(name);
        return character;
    }

    public List<Character> findByAge(Integer age) throws RecordNotExistsException {
        List<Character> list= characterService.findByAge(age);
        return list;
    }

    public List<Character> findByMovieId(Integer idMovie) throws RecordNotExistsException {

        List<Character> list= characterService.findByIdMovie(idMovie);
        return list;
    }
}
