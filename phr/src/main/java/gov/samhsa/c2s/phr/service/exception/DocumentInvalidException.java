package gov.samhsa.c2s.phr.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DocumentInvalidException extends RuntimeException {
    public DocumentInvalidException() {}

    public DocumentInvalidException(String message) {
        super(message);
    }
}
