package bg.softuni.WeddingSite.services;

import bg.softuni.WeddingSite.models.Photographer;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.Wedding;
import bg.softuni.WeddingSite.models.dtos.WeddingRegDTO;
import bg.softuni.WeddingSite.repository.PhotographerRepository;
import bg.softuni.WeddingSite.repository.UserRepository;
import bg.softuni.WeddingSite.repository.WeddingRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class WeddingService {
    private final WeddingRepository weddingRepository;
    private final UserRepository userRepository;
    private final PhotographerRepository photographerRepository;

    public WeddingService(WeddingRepository weddingRepository, UserRepository userRepository, PhotographerRepository photographerRepository) {
        this.weddingRepository = weddingRepository;
        this.userRepository = userRepository;
        this.photographerRepository = photographerRepository;
    }

    public Boolean addingWedding(WeddingRegDTO weddingRegDTO, Principal principal){
        User groom = this.userRepository.findByUsername(weddingRegDTO.getGroomName()).orElseThrow(()->new UsernameNotFoundException("User not found! Groom"));
        User bride = this.userRepository.findByUsername(weddingRegDTO.getBrideName()).orElseThrow(()->new UsernameNotFoundException("User not found! Bride"));
        Optional<User> photographUsername = this.userRepository.findByUsername(weddingRegDTO.getPhotographer());
        User principalUser = this.userRepository
                .findByUsername(principal.getName())
                .orElseThrow(()->new UsernameNotFoundException("User not found! principal"));

        Wedding wedding = new Wedding();
        Photographer photographer = new Photographer();

        if (photographUsername.isEmpty()){
            User newPhotographerUser = new User();
            newPhotographerUser.setUsername(weddingRegDTO.getPhotographer());
            photographer.setUser(newPhotographerUser);
            photographer.setCreator(newPhotographerUser);
        }else{
            photographer.setUser(photographUsername.get());
            photographer.setCreator(photographUsername.get());

        }
        photographerRepository.save(photographer);
        wedding.setPhotographer(photographer);

        wedding.setBride(bride);
        wedding.setGroom(groom);
        wedding.setCreator(principalUser);
        wedding.setWeddingDate(weddingRegDTO.getWeddingDate());
        weddingRepository.save(wedding);

        return true;
    }

    public List<Wedding> findAllWeddings() {
        return this.weddingRepository.findAll();
    }

    public Optional<Wedding> getWeddingById(Long weddingId) {
        return this.weddingRepository.findById(weddingId);
    }

    public List<Wedding> findAllWeddingsWereUserIdGroomOrBride(Long id) {
        return weddingRepository.findAllWeddingsWereUserIdIsGroomOrBride(id);
    }
}
