package alkemi.disney.Disney.repository;

import alkemi.disney.Disney.model.Character;
import alkemi.disney.Disney.model.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer> {

    Movie findByTitle(String tile);

    @Query(value = "SELECT *\n" +
            "    FROM movies m\n" +
            "    inner join genre_by_movie gbm on gbm.movie_id = m.id\n" +
            "    where gbm.genre_id =?1\n"
            , nativeQuery = true)
    List<Movie> findByGenreId(Integer genre);

    @Modifying
    @Transactional
    @Query(value = "update movies SET title =?1, image =?2, creation_movie =?3, qualification=?4  WHERE id = ?5",nativeQuery = true)
    Integer updateMovie(String title, String image, Date creation, Integer qualification, Integer id);
}
