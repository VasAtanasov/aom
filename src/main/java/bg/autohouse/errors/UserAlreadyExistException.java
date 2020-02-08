package bg.autohouse.errors;

import bg.autohouse.common.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = Constants.EXCEPTION_USERNAME_OR_EMAIL_EXISTS)
public class UserAlreadyExistException extends BaseException {

    public UserAlreadyExistException(String message) {
        super(HttpStatus.CONFLICT.value(), message);
    }
}