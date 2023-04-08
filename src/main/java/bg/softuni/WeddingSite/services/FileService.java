package bg.softuni.WeddingSite.services;

import bg.softuni.WeddingSite.models.Picture;
import bg.softuni.WeddingSite.models.User;
import bg.softuni.WeddingSite.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final AuthService authService;

    public FileService(FileRepository fileRepository, AuthService authService) {
        this.fileRepository = fileRepository;
        this.authService = authService;
    }

    public Long saveFile(MultipartFile file, User user) throws IOException {
        if (file.isEmpty()){
            return 0L;
        }
        Picture picture = new Picture();

        if (user.getPicture() != null){
            picture = user.getPicture();
        }


        picture.setFileName(file.getOriginalFilename());
        picture.setContentType(file.getContentType());
        picture.setData(file.getBytes());

        return fileRepository.save(picture).getId();
    }

    public Optional<Picture> findById(Long id){
        return this.fileRepository.findById(id);
    }
}
