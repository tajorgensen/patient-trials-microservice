package com.tajorgensen.patienttrials.common.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private ErrorDetails errorDetails;

    public ResourceNotFoundException(String errorCode, String message) {
        super(String.format("%s - %s", errorCode, message));
        this.errorDetails = ErrorDetails.builder().errorCode(errorCode).message(message).build();
    }
}
