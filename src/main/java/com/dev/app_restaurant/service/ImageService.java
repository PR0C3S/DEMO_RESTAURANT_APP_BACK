package com.dev.app_restaurant.service;

import com.dev.app_restaurant.entity.Image;
import com.dev.app_restaurant.exception.BadRequestException;
import com.dev.app_restaurant.exception.ResourceNotFoundException;
import com.dev.app_restaurant.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    private ImageRepository repository;
    @Value("${files.images-path}")
    private String IMAGES_PATH;

    @Autowired
    ResourceLoader resourceLoader;

    public Image create(MultipartFile file) throws IOException {
        Path directoy = Paths.get(IMAGES_PATH);
        if(!Files.exists(directoy)){
            Files.createDirectories(directoy);
        }
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        String filePath=directoy+File.separator+uuidAsString;
        Image fileData=repository.save(new Image(null,file.getOriginalFilename(),file.getContentType(),filePath));
        file.transferTo(new File(filePath));
        return fileData;
    }

    public Image findByName(String fileName) {
        Image fileData = repository.findByName(fileName);
        if(fileData == null){
            throw new ResourceNotFoundException("El archivo no existe.");
        }
        return fileData;
    }

    public Image findByID(Integer id) {
        Image fileData = repository.findById(id).orElse(null);
        if(fileData == null){
            throw new ResourceNotFoundException("El archivo no existe.");
        }
        return fileData;
    }

    public void deleteByID(Integer id) {
        Image fileData = findByID(id);
        File file = new File(fileData.getFilePath());

        if (file.delete()) {
            System.out.println("File deleted successfully");
            repository.delete(fileData);
        }
        else {
            System.out.println("Failed to delete the file");
            throw new BadRequestException("Algo salio mal eliminando la foto, intentalo mas tarde.");
        }
    }





}