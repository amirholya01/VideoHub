package com.videohub.videohub.exception;

/*
 * This class represents a custom exception for file storage-related errors.
 * It extends the RuntimeException class to indicate that it's an unchecked exception.
 */
public class FileStorageException  extends RuntimeException{

    /*
     * Constructs a new FileStorageException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public FileStorageException(String message) {
        super(message);
    }


    /*
     * Constructs a new FileStorageException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause   the cause (which is saved for later retrieval by the getCause() method)
     */
    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
