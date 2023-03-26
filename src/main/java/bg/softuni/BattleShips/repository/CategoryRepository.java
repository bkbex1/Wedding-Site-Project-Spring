package bg.softuni.BattleShips.repository;

import bg.softuni.BattleShips.models.Category;
import bg.softuni.BattleShips.models.ShipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(ShipType type);
}
