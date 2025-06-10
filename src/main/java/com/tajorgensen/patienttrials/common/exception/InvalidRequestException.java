package com.tajorgensen.patienttrials.common.exception;

import lombok.Getter;

@Getter
public class InvalidRequestException extends RuntimeException {

    private ErrorDetails errorDetails;

    public InvalidRequestException(String errorCode, String message) {
        super(String.format("%s - %s", errorCode, message));
        this.errorDetails = ErrorDetails.builder().errorCode(errorCode).message(message).build();
    }
}
