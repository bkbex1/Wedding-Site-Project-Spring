package bg.softuni.WeddingSite.services;

import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.Wedding;
import bg.softuni.WeddingSite.models.dtos.WeddingRegDTO;
import bg.softuni.WeddingSite.repository.UserRepository;
import bg.softuni.WeddingSite.repository.WeddingRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeddingService {
    private final WeddingRepository weddingRepository;
    private final UserRepository userRepository;

    public WeddingService(WeddingRepository weddingRepository, UserRepository userRepository) {
        this.weddingRepository = weddingRepository;
        this.userRepository = userRepository;
    }

    public Boolean addingWedding(WeddingRegDTO weddingRegDTO){
        User groom = this.userRepository.findByUsername(weddingRegDTO.getGroomName()).orElseThrow(()->new UsernameNotFoundException("User not found!"));
        User bride = this.userRepository.findByUsername(weddingRegDTO.getBrideName()).orElseThrow(()->new UsernameNotFoundException("User not found!"));

        Wedding wedding = new Wedding();
        wedding.setBride(bride);
        wedding.setGroom(groom);
        wedding.setWeddingDate(weddingRegDTO.getWeddingDate());
        weddingRepository.save(wedding);

        return true;
    }
}
