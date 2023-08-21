package bg.softuni.WeddingSite.services;

import bg.softuni.WeddingSite.models.Picture;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.repository.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @Mock
    FileRepository fileRepository;

    @Mock
    AuthService authService;

    @Mock
    MultipartFile file;

    @InjectMocks
    private FileService fileService;
    private FileService toTest;

    @BeforeEach
    void setUp(){
        toTest = new FileService(fileRepository, authService);
    }

    @Test
    public void testSaveFileEmptyFile() throws IOException {
        MockMultipartFile emptyFile = new MockMultipartFile("empty.txt", "".getBytes());
        User user = new User();

        Long result = fileService.saveFile(emptyFile, user);

        assertEquals(0L, result);
        verify(fileRepository, never()).save(any());
    }

    @Test
    public void testSaveFileUserHasPicture() throws IOException {
        MockMultipartFile nonEmptyFile = new MockMultipartFile("file.txt", "file content".getBytes());
        User user = new User();
        Picture picture = new Picture();
        user.setPicture(picture);

        when(fileRepository.save(picture)).thenReturn(picture);

        Long result = fileService.saveFile(nonEmptyFile, user);

        assertNull(result);
        assertEquals(picture.getId(), result);
        verify(fileRepository).save(picture);
    }
    @Test
    public void testSaveFileUserDoesNotHavePicture() throws IOException {
        MockMultipartFile nonEmptyFile = new MockMultipartFile("file.txt", "file content".getBytes());
        User user = new User();

        when(fileRepository.save(any())).thenReturn(new Picture());

        Long result = fileService.saveFile(nonEmptyFile, user);

        assertNull(result);
        verify(fileRepository).save(any());
    }
    @Test
    void testFindById() throws IOException {
        Picture picture = new Picture();
        picture.setFileName("File");
        when(fileRepository.findById(1L)).thenReturn(Optional.of(picture));
        assertEquals(toTest.findById(1L).get().getFileName(), picture.getFileName());

    }
}
