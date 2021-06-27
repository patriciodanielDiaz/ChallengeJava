package alkemi.disney.Disney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import alkemi.disney.Disney.model.Character;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CharacterRepository  extends JpaRepository<Character,Integer> {

    @Modifying
    @Transactional
    @Query(value = "update characters SET image =?1, name =?2, weight =?3, story =?4, age=?5  WHERE id = ?6",nativeQuery = true)
    Integer updateCharacter(String image,String name,Float weight,String story,Integer age, Integer id);

    Character findByName(String name);

    @Query(value = "SELECT * FROM characters c WHERE c.age=?1", nativeQuery = true)
    List<Character> findByAge(Integer age);

    @Query(value = "SELECT *\n" +
            "    FROM characters c\n" +
            "    inner join movies_by_characters mbc on mbc.character_id = c.id\n" +
            "    where mbc.movie_id =?1\n"
                , nativeQuery = true)
    List<Character> findByIdMovie(Integer idMovie);
}
