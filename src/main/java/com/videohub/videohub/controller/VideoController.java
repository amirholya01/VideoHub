package com.videohub.videohub.controller;


import com.videohub.videohub.domain.Video;
import com.videohub.videohub.service.VideoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;


    @CrossOrigin(origins = "*")
    @RestController
    @RequestMapping("video")
    public class VideoController{

        @Autowired
        private VideoService videoService;

        @CrossOrigin
        @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @PreAuthorize("hasRole('ADMIN')")
        @SecurityRequirement(name = "Bearer Authentication")
        public ResponseEntity<?> uploadVideo(
                @RequestParam("file") MultipartFile file,
                String title,
                String categoryId) {
            return ResponseEntity.ok(videoService.uploadVideo(file, title, categoryId));
        }

        @GetMapping
        @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
        @SecurityRequirement(name = "Bearer Authentication")
        public List<Video> videos() {
            return videoService.findAllVideo();
        }

        @GetMapping("{id}")
        @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
        @SecurityRequirement(name = "Bearer Authentication")
        public Video findById(@PathVariable("id") String id, HttpServletRequest request) {
            return videoService.findById(id, request);
        }

        @CrossOrigin
        @GetMapping("/download/{id:.+}")
        @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
        @SecurityRequirement(name = "Bearer Authentication")
        public ResponseEntity<Resource> downloadFile(@PathVariable String id, HttpServletRequest request) {
            return videoService.downloadFile(id, request);
        }


        @DeleteMapping("{id}")
        @PreAuthorize("hasRole('ADMIN')")
        @SecurityRequirement(name = "Bearer Authentication")
        public void deleteVideoById(@PathVariable String id) {
            videoService.deleteVideoById(id);
        }

        @GetMapping("/category/{id}")
        public ResponseEntity<List<Video>> findVideoByCategoryId(@PathVariable("id") String id) {
            return new ResponseEntity<>(videoService.findVideoByCategoryId(id), HttpStatus.OK);
        }

        @GetMapping("downloadVideo/{id}")
        public ResponseEntity getVideo(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
            return videoService.getByteArrayResourceResponseEntity(id);
        }
    }


