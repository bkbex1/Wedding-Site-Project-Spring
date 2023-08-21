package bg.softuni.WeddingSite.services;

import bg.softuni.WeddingSite.models.Person;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.models.Wedding;
import bg.softuni.WeddingSite.models.dtos.WeddingRegDTO;
import bg.softuni.WeddingSite.repository.PhotographerRepository;
import bg.softuni.WeddingSite.repository.UserRepository;
import bg.softuni.WeddingSite.repository.WeddingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeddingServiceTest {

    @Mock
    WeddingRepository weddingRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    PhotographerRepository photographerRepository;

    private WeddingService toTest;

    @BeforeEach
    void setUp(){
        toTest = new WeddingService(weddingRepository, userRepository, photographerRepository);
    }

    @Test
    void testAddingWedding(){
        WeddingRegDTO weddingRegDTO = new WeddingRegDTO();
        User creator = new User();
        creator.setUsername("Creator");

        weddingRegDTO.setBrideName("Preslava");
        weddingRegDTO.setGroomName("Boris");
        weddingRegDTO.setPhotographer("Photographer");

        when(userRepository.findByUsername(weddingRegDTO.getBrideName())).thenReturn(Optional.of(new User()));
        when(userRepository.findByUsername(weddingRegDTO.getGroomName())).thenReturn(Optional.of(new User()));
        when(userRepository.findByUsername(weddingRegDTO.getPhotographer())).thenReturn(Optional.of(new User()));

        assertTrue(toTest.addingWedding(weddingRegDTO,creator));

        Mockito.verify(photographerRepository).save(any());
    }

    @Test
    void testFindAllWeddings(){
        when(weddingRepository.findAll()).thenReturn(List.of(new Wedding(), new Wedding(), new Wedding()));
        assertEquals(3, toTest.findAllWeddings().size());
    }

    @Test
    void testGetWeddingById(){
        Wedding wedding = new Wedding();
        when(weddingRepository.findById(1L)).thenReturn(Optional.of(wedding));
        assertEquals(toTest.getWeddingById(1L), Optional.of(wedding));
    }

    @Test
    void testFindAllWeddingsWereUserIdGroomOrBride(){
        Wedding wedding1 = new Wedding();
        Wedding wedding2 = new Wedding();

        Person groom = new User();
        Person bride1 = new User();
        Person bride2 = new User();

        bride1.setFirstName("Kalina");
        bride2.setFirstName("Petra");

        groom.setFirstName("Boris");
        wedding1.setGroom(groom);
        wedding1.setBride(bride1);

        wedding2.setGroom(groom);
        wedding2.setBride(bride2);


        when(weddingRepository.findWeddingsByGroomOrBrideId(1L)).thenReturn(List.of(wedding1, wedding2));
        List<Wedding> testWeddings = toTest.findAllWeddingsWereUserIdGroomOrBride(1L);

        assertEquals(testWeddings.get(0).getGroom().getFirstName(), "Boris");
        assertEquals(testWeddings.get(0).getBride().getFirstName(), "Kalina");

        assertEquals(testWeddings.get(1).getGroom().getFirstName(), "Boris");
        assertEquals(testWeddings.get(1).getBride().getFirstName(), "Petra");
    }
}
