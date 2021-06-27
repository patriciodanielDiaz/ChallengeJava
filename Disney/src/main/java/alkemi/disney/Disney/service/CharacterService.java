package alkemi.disney.Disney.service;

import alkemi.disney.Disney.exception.CharacterNotFound;
import alkemi.disney.Disney.exception.CharacterNotUpdated;
import alkemi.disney.Disney.exception.RecordNotExistsException;
import alkemi.disney.Disney.exception.UncreatedCharacter;
import alkemi.disney.Disney.model.Character;
import alkemi.disney.Disney.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

    private CharacterRepository characterRepository;

    @Autowired
    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<Character> getAll() throws RecordNotExistsException {
        List<Character> list = characterRepository.findAll();
        return Optional.ofNullable(list).orElseThrow(() -> new RecordNotExistsException("empty character list.."));
    }

    public Character addCharacter(Character character) throws UncreatedCharacter {
        Character created =characterRepository.save(character);
        return Optional.ofNullable(created).orElseThrow(() -> new UncreatedCharacter("there was an error creating character..."));
    }

    public Character findById(Integer id) throws CharacterNotFound {
        Optional<Character> character= characterRepository.findById(id);
        return character.orElseThrow(() -> new CharacterNotFound("there was an error searching character..."));
    }

    public Integer updateCharacter(Character character) {
        return characterRepository.updateCharacter(character.getImage(),character.getName(),character.getWeight(),character.getStory(),character.getAge(),character.getId());
    }

    public void deleteCharacter(Character character) {
        characterRepository.delete(character);
    }

    public Character findByName(String name) throws CharacterNotFound {
        Character character= characterRepository.findByName(name);
        return Optional.ofNullable(character).orElseThrow(() -> new CharacterNotFound("there was an error searching character..."));
    }

    public List<Character> findByAge(Integer age) throws RecordNotExistsException {
        List<Character> list = characterRepository.findByAge(age);
        return Optional.ofNullable(list).orElseThrow(() -> new RecordNotExistsException("empty character list.."));
    }

    public List<Character> findByIdMovie(Integer idMovie) throws RecordNotExistsException {
        List<Character> list = characterRepository.findByIdMovie(idMovie);
        return Optional.ofNullable(list).orElseThrow(() -> new RecordNotExistsException("empty character list.."));
    }
}
