package bg.softuni.BattleShips.services;

import bg.softuni.BattleShips.models.Category;
import bg.softuni.BattleShips.models.Ship;
import bg.softuni.BattleShips.models.ShipType;
import bg.softuni.BattleShips.models.User;
import bg.softuni.BattleShips.models.dtos.CreateShipDTO;
import bg.softuni.BattleShips.repository.CategoryRepository;
import bg.softuni.BattleShips.repository.ShipRepository;
import bg.softuni.BattleShips.repository.UserRepository;
import bg.softuni.BattleShips.session.LoggedUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShipService {
    private ShipRepository shipRepository;
    private CategoryRepository categoryRepository;
    private LoggedUser loggedUser;
    private UserRepository userRepository;

    public ShipService(ShipRepository shipRepository,
                       CategoryRepository categoryRepository,
                       LoggedUser loggedUser, UserRepository userRepository) {
        this.shipRepository = shipRepository;
        this.categoryRepository = categoryRepository;
        this.loggedUser = loggedUser;
        this.userRepository = userRepository;
    }

    public boolean create(CreateShipDTO createShipDTO){

        Optional<Ship> byName = this.shipRepository.findByName(createShipDTO.getName());
        if (byName.isPresent()){
            return false;
        }

        ShipType type = switch (createShipDTO.getCategory()){
            case 0 -> ShipType.BATTLE;
            case 1 -> ShipType.CARGO;
            case 2 -> ShipType.PATROL;
            default -> ShipType.BATTLE;
        };

        Category categoryDB = this.categoryRepository.findByName(type);
        Optional<User> owner = this.userRepository.findById(this.loggedUser.getId());
        Ship ship = new Ship();
        ship.setName(createShipDTO.getName());
        ship.setCreated(createShipDTO.getCreated());
        ship.setPower(createShipDTO.getPower());
        ship.setHealth(createShipDTO.getHealth());
        ship.setOwner(owner.get());
        ship.setCategory(categoryDB);

        this.shipRepository.save(ship);
        return true;
    }
}
