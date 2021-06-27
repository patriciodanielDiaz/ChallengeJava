package alkemi.disney.Disney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import alkemi.disney.Disney.model.Character;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CharacterDto {

    private String name;
    private String image;

    public static List<CharacterDto> transferToCharacterDto(List<Character> characters){

        List<CharacterDto> characterDtos= new ArrayList<>();
        characters.forEach((ch)->{CharacterDto tranferDto= new CharacterDto();
                                   tranferDto.setName(ch.getName());
                                   tranferDto.setImage(ch.getImage());
                                   characterDtos.add(tranferDto);});

        return characterDtos;
    }
}
