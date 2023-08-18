package bg.softuni.WeddingSite.repository;

import bg.softuni.WeddingSite.models.Wedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeddingRepository extends JpaRepository<Wedding, Long> {

    @Query("SELECT w FROM Wedding w WHERE w.groom.id = :id OR w.bride.id = :id")
    List<Wedding> findWeddingsByGroomOrBrideId(Long id);

}
