package com.videohub.videohub.controller;

import com.videohub.videohub.domain.Video;
import com.videohub.videohub.service.VideoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Controller class for handling video-related endpoints.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * Endpoint for uploading a video file.
     * @param file The video file to upload.
     * @param title The title of the video.
     * @param categoryId The category ID of the video.
     * @return ResponseEntity with the result of the upload operation.
     */
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

    /**
     * Endpoint for retrieving all videos.
     * @return List of videos.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<Video> videos() {
        return videoService.findAllVideo();
    }

    /**
     * Endpoint for finding a video by its ID.
     * @param id The ID of the video to find.
     * @param request HttpServletRequest.
     * @return The found video.
     */
    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public Video findById(@PathVariable("id") String id, HttpServletRequest request) {
        return videoService.findById(id, request);
    }

    /**
     * Endpoint for downloading a video file.
     * @param id The ID of the video to download.
     * @param request HttpServletRequest.
     * @return ResponseEntity with the video file.
     */
    @CrossOrigin
    @GetMapping("/download/{id:.+}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Resource> downloadFile(@PathVariable String id, HttpServletRequest request) {
        return videoService.downloadFile(id, request);
    }

    /**
     * Endpoint for deleting a video by its ID.
     * @param id The ID of the video to delete.
     */
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public void deleteVideoById(@PathVariable String id) {
        videoService.deleteVideoById(id);
    }

    /**
     * Endpoint for finding videos by category ID.
     * @param id The ID of the category.
     * @return ResponseEntity with the list of videos.
     */
    @GetMapping("/category/{id}")
    public ResponseEntity<List<Video>> findVideoByCategoryId(@PathVariable("id") String id) {
        return new ResponseEntity<>(videoService.findVideoByCategoryId(id), HttpStatus.OK);
    }

    /**
     * Endpoint for streaming a video.
     * @param id The ID of the video to stream.
     * @param response HttpServletResponse.
     * @return ResponseEntity with the video file.
     * @throws IOException If an I/O error occurs.
     */
    @GetMapping("downloadVideo/{id}")
    public ResponseEntity getVideo(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        return videoService.getByteArrayResourceResponseEntity(id);
    }
}
