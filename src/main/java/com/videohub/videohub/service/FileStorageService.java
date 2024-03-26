package com.videohub.videohub.service;

import com.videohub.videohub.exception.FileStorageException;
import com.videohub.videohub.exception.VideoNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;


/**
 * Service class for managing file storage operations.
 */
@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Value("${videoHub.file.path}")
    public String path;

    @Value("${videoHub.file.extension}")
    private String[] fileExtension;

    /**
     * Constructor to initialize file storage location.
     */
    public FileStorageService() {

        // Define file storage location
        this.fileStorageLocation = Paths.get("uploads")
                .toAbsolutePath().normalize();

        // Create directory for file storage if it doesn't exist
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            // Throw FileStorageException if directory creation fails
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }



    /**
     * Store file in the file storage location.
     *
     * @param file MultipartFile to be stored
     * @return Name of the stored file
     */
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check for invalid characters in file name
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path seq" + fileName);
            }
            // Define target location for storing the file
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            // Copy file to the target location
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            // Throw FileStorageException if storing file fails
            throw new FileStorageException("could not store file " + fileName + ". Pls try again!", ex);
        }
    }



    /**
     * Load file as a resource from the file storage location.
     *
     * @param fileName Name of the file to be loaded
     * @return Resource representing the loaded file
     */
    public Resource loadFileAsResource(String fileName) {
        try {
            // Resolve file path
            File file = new File(path + fileName);
            Path filePath = Paths.get(file.getAbsolutePath());
            // Create Resource object from file path
            Resource resource = new UrlResource(filePath.toUri());
            // Check if resource exists
            if (resource.exists()) {
                return resource;
            } else {
                // Throw VideoNotFoundException if resource not found
                throw new VideoNotFoundException("Video not found, video name: " + fileName);
            }
        } catch (MalformedURLException ex) {
            // Throw VideoNotFoundException if URL for resource is malformed
            throw new VideoNotFoundException("Video not found, video name: " + fileName);
        }
    }



    /**
     * Check if the file format is valid.
     *
     * @param fileContentType Content type of the file
     * @return True if the file format is valid, otherwise false
     */
    public boolean checkFormat(String fileContentType) {
        String[] fileFormat = fileContentType.split("/");
        // Check if file format is video and matches allowed extensions
        if (fileFormat[0].equals("video")) {
            if (Arrays.asList(fileExtension).contains(fileFormat[1])) {
                return true;
            }
        }
        return false;
    }



    /**
     * Extract the file extension from the content type.
     *
     * @param fileContentType Content type of the file
     * @return File extension
     */
    public String prefixFile(String fileContentType) {
        String[] fileFormat = fileContentType.split("/");
        // Extract file extension
        if (fileFormat[0].equals("video")) {
            return fileFormat[1];
        }
        return null;
    }
}
