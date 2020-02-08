package bg.autohouse.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Resource not found!")
public class NotFoundException extends BaseException {
    public NotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
