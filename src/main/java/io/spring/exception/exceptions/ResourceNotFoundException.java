package io.spring.exception.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Class entity, String identifier, String id) {
        super("No " + entity.getSimpleName() + " with this " + identifier + " " + id);
    }


    public ResourceNotFoundException(String message) {
        super(message);
    }
}
