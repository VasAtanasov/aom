package com.github.vaatech.aom.web.error;

import jakarta.validation.ValidationException;

public class MessageExposableValidationException extends ValidationException {
    public MessageExposableValidationException(final String errorMessage) {
        super(errorMessage);
    }
}
