package bg.softuni.BattleShips.seeders;

import bg.softuni.BattleShips.models.Category;
import bg.softuni.BattleShips.models.ShipType;
import bg.softuni.BattleShips.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategorySeeder implements CommandLineRunner {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategorySeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0){
            List<Category> categories = Arrays.stream(ShipType.values())
                    .map(Category::new).collect(Collectors.toList());

            this.categoryRepository.saveAll(categories);
        }
    }
}
