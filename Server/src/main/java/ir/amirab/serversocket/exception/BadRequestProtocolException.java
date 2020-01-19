package ir.amirab.serversocket.exception;

public class BadRequestProtocolException extends Exception {
    public BadRequestProtocolException(String description){
        super(description);
    }
}
