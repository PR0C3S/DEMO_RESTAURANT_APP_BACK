package com.dev.app_restaurant.controller;


import com.dev.app_restaurant.entity.Image;
import com.dev.app_restaurant.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService service;


    @GetMapping("/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable Integer id) throws Exception {

        Image image = service.findByID(id);
        // Path to the image file
        Path path = Paths.get(image.getFilePath());
        // Load the resource
        Resource resource = new UrlResource(path.toUri());
        // Return ResponseEntity with image content type
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}
