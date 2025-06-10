package com.tajorgensen.patienttrials.common.exception;

public class ErrorConstants {

    public enum PatientErrorCode {
        INVALID_CREATE_REQUEST("001"),
        INVALID_UPDATE_REQUEST("002"),
        UPDATE_ID_NOT_FOUND("003"),
        DELETE_ID_NOT_FOUND("004"),
        GET_ID_NOT_FOUND("005");

        private final String code;

        private static final String prefix = "P";

        PatientErrorCode(String code) {
            this.code = prefix + code;
        }

        public String getCode() {
            return code;
        }
    }

    public enum DrugErrorCode {
        INVALID_CREATE_REQUEST("001"),
        INVALID_UPDATE_REQUEST("002"),
        UPDATE_ID_NOT_FOUND("003"),
        DELETE_ID_NOT_FOUND("004"),
        GET_ID_NOT_FOUND("005");

        private final String code;

        private static final String prefix = "D";

        DrugErrorCode(String code) {
            this.code = prefix + code;
        }

        public String getCode() {
            return code;
        }
    }

    public enum TrialErrorCode {
        INVALID_CREATE_REQUEST("001"),
        INVALID_UPDATE_REQUEST("002"),
        UPDATE_ID_NOT_FOUND("003"),
        DELETE_ID_NOT_FOUND("004"),
        GET_ID_NOT_FOUND("005");

        private final String code;

        private static final String prefix = "T";

        TrialErrorCode(String code) {
            this.code = prefix + code;
        }

        public String getCode() {
            return code;
        }
    }

    public enum PatientTrialErrorCode {
        INVALID_CREATE_REQUEST("001"),
        INVALID_UPDATE_REQUEST("002"),
        UPDATE_ID_NOT_FOUND("003"),
        DELETE_ID_NOT_FOUND("004"),
        GET_ID_NOT_FOUND("005");

        private final String code;

        private static final String prefix = "PT";

        PatientTrialErrorCode(String code) {
            this.code = prefix + code;
        }

        public String getCode() {
            return code;
        }
    }

    public enum AdverseEventErrorCode {
        INVALID_CREATE_REQUEST("001"),
        INVALID_UPDATE_REQUEST("002"),
        UPDATE_ID_NOT_FOUND("003"),
        DELETE_ID_NOT_FOUND("004"),
        GET_ID_NOT_FOUND("005");

        private final String code;

        private static final String prefix = "AE";

        AdverseEventErrorCode(String code) {
            this.code = prefix + code;
        }

        public String getCode() {
            return code;
        }
    }
}
