package com.videohub.videohub.service;

import com.videohub.videohub.domain.Category;
import com.videohub.videohub.domain.User;
import com.videohub.videohub.domain.Video;
import com.videohub.videohub.repository.CategoryRepository;
import com.videohub.videohub.repository.UserRepository;
import com.videohub.videohub.repository.VideoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;



/**
 * Service class for managing video-related operations.
 */
@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${videoHub.file.path}")
    private String path;



    /**
     * Uploads a video file.
     *
     * @param file       MultipartFile representing the video file
     * @param title      Title of the video
     * @param categoryId ID of the category to which the video belongs
     * @return ResponseEntity containing the uploaded video
     */
    public ResponseEntity<?> uploadVideo(MultipartFile file, String title, String categoryId) {
        Video video = new Video();
        Category category = new Category();

        // Retrieve category by ID
        Optional<Category> categoryData = categoryRepository.findById(categoryId);
        if (categoryData.isPresent()) {
            video.setCategory(categoryData.get());
            category = categoryData.get();
        }

        // Check if video with same name exists
        Optional<List<Video>> videoData = videoRepository.findByUrl(file.getOriginalFilename());
        if (videoData.isPresent()) {
            if (videoData.get().size() > 0) {
                return new ResponseEntity<>("video name duplicate", HttpStatus.BAD_REQUEST);
            }
        }

        // Check if file format is valid
        if (fileStorageService.checkFormat(file.getContentType())) {
            // Generate unique file name
            String fileName = extractedNameFile(fileStorageService.prefixFile(file.getContentType())).replace(":", "_");

            // Retrieve current user
            User user = new User();
            Optional<User> userOpt = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            if (userOpt.isPresent()) {
                user = userOpt.get();
            }
            video.setUser(user);
            video.setTitle(title);
            video.setSize(file.getSize());
            video.setUrl(fileName);
            video.setCreated(new Date());
            video.setModified(new Date());

            // Save the video
            Video videoCreated = videoRepository.save(video);

            try {
                Path path = Paths.get(fileStorageService.path, fileName);
                Files.copy(file.getInputStream(), path);
            } catch (Exception e) {
                // todo
            }

            return ResponseEntity.ok(video);
        }
        return new ResponseEntity<>("format not valid", HttpStatus.BAD_REQUEST);
    }


    /**
     * Generates a unique file name based on current date and file extension.
     *
     * @param prefix File extension
     * @return Unique file name
     */
    private static String extractedNameFile(String prefix) {
        String pattern = "MM-dd-yyyy_HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todyAsString = df.format(today) + "." + prefix;
        return todyAsString;
    }

    /**
     * Retrieves all videos.
     *
     * @return List of all videos
     */
    public List<Video> findAllVideo() {
        return videoRepository.findAll();
    }


    /**
     * Finds a video by ID.
     *
     * @param id      ID of the video to find
     * @param request HttpServletRequest
     * @return Found Video object
     */
    public Video findById(String id, HttpServletRequest request) {
        Optional<Video> video = videoRepository.findById(id);
        return video.isPresent() ? video.get() : null;
    }


    /**
     * Downloads a video file.
     *
     * @param id      ID of the video to download
     * @param request HttpServletRequest
     * @return ResponseEntity containing the downloaded video
     */
    public ResponseEntity<Resource> downloadFile(String id, HttpServletRequest request) {
        Optional<Video> video = videoRepository.findById(id);
        Resource resource = fileStorageService.loadFileAsResource(video.get().getUrl());

        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // TODO: Handle exception
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"", resource.getFilename() + "\"")
                .body(resource);

    }


    /**
     * Deletes a video by ID.
     *
     * @param id ID of the video to delete
     */
    public void deleteVideoById(String id) {
        videoRepository.deleteById(id);
    }


    /**
     * Finds videos by category ID.
     *
     * @param categoryId ID of the category
     * @return List of videos belonging to the specified category
     */
    public List<Video> findVideoByCategoryId(String categoryId) {
        return videoRepository.findByCategoryId(categoryId);
    }


    /**
     * Retrieves video file as ByteArrayResource.
     *
     * @param id ID of the video
     * @return ResponseEntity containing the video file as ByteArrayResource
     * @throws IOException
     */
    public ResponseEntity<ByteArrayResource> getByteArrayResourceResponseEntity(String id) throws IOException {
        Video video = new Video();
        Optional<Video> videoData = videoRepository.findById(id);
        if (videoData.isPresent()) {
            video = videoData.get();
        }
        File file = new File(path + video.getUrl());
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return ResponseEntity.ok().headers(this.headers(video.getUrl())).contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }


    /**
     * Generates HttpHeaders for video file download.
     *
     * @param name Name of the file
     * @return HttpHeaders for video file download
     */
    private HttpHeaders headers(String name) {
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return header;

    }
}
