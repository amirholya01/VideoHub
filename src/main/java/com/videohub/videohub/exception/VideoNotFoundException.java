package com.videohub.videohub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/*
 * This class represents a custom exception for video not found errors.
 * It extends the RuntimeException class to indicate that it's an unchecked exception.
 * It is annotated with @ResponseStatus to automatically set the HTTP status code to 404 (NOT_FOUND)
 * when this exception is thrown from a controller method.
 */


@ResponseStatus(HttpStatus.NOT_FOUND)
public class VideoNotFoundException extends RuntimeException{

    /*
     * Constructs a new VideoNotFoundException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public VideoNotFoundException(String message) {
        super(message);
    }


    /*
     * Constructs a new VideoNotFoundException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause   the cause (which is saved for later retrieval by the getCause() method)
     */
    public VideoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
