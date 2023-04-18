package bg.softuni.WeddingSite.repository;

import bg.softuni.WeddingSite.models.Wedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeddingRepository extends JpaRepository<Wedding, Long> {
    @Query(value = "SELECT * FROM Weddings WHERE groom_id = ?1 or bride_id = ?1", nativeQuery = true)
    List<Wedding> findAllWeddingsWereUserIdIsGroomOrBride(@Param("q") Long id);
//"SELECT w from Wedding w where groom = '%:q%' or bride = '%:q%'"
}
