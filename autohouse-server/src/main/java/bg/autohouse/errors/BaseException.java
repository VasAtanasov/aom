package bg.autohouse.errors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException {

    private int status;

    BaseException(int status, String message) {
        super(message);
        this.setStatus(status);
    }
}
