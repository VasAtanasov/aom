package bg.autohouse.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static bg.autohouse.common.Constants.GENERAL_SERVICE_ERROR;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = GENERAL_SERVICE_ERROR)
public class ServiceGeneralException extends RuntimeException {

    public ServiceGeneralException(String message) {
        super(message);
    }
}
