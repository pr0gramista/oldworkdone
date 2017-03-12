package pl.pr0gramista.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class StrangeOAuthException extends RuntimeException {
    public StrangeOAuthException(String message) {
        super(message);
    }
}
