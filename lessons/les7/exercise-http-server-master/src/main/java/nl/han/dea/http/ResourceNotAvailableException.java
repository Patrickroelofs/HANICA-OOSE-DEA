package nl.han.dea.http;

public class ResourceNotAvailableException extends Exception {

    public ResourceNotAvailableException(String filename) {
        super(filename + " Not available");
    }
}
