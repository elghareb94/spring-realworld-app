package io.spring.exception.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Class entity, String identifier, String id) {
        super("No " + entity.getName() + " with this " + identifier + " " + id);
    }

}
