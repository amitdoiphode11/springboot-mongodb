package com.eaglesoft.springbootmongodb.controller;

import com.eaglesoft.springbootmongodb.collection.Photo;
import com.eaglesoft.springbootmongodb.service.PhotoService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;

@RestController
@RequestMapping("/photo")
public class PhotoController {
    private PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping
    public String addPhoto(@RequestParam("image") MultipartFile image) throws IOException {
        String id = photoService.addPhoto(image.getOriginalFilename(), image);
        return id;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadPhoto(@PathVariable("id") String id) {
        Photo photo = photoService.getPhoto(id);
        Resource resource = new ByteArrayResource(photo.getPhoto().getData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + photo.getTitle() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
