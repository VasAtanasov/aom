package com.github.vaatech.aom.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Exception mapped to Conflict HTTP status code (409) */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class VersionConflictException extends RuntimeException {

  public VersionConflictException() {
    super();
  }

  public VersionConflictException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public VersionConflictException(final String message) {
    super(message);
  }

  public VersionConflictException(final Throwable cause) {
    super(cause);
  }
}
