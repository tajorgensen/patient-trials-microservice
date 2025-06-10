package com.tajorgensen.patienttrials.common.constants;

public class ApplicationConstants {

    public enum TreatmentStatus {
        ACTIVE,
        COMPLETED,
        WITHDRAWN
    }

    public enum EventSeverity {
        MILD,
        MODERATE,
        SEVERE,
        UNKNOWN
    }

    public enum UnitsOfMeasurement {
        MILLIGRAMS,
        MICROGRAMS,
        MILLILITERS,
        GRAMS
    }

    public enum Gender {
        MALE('M'),
        FEMALE('F');

        private Character code;

        Gender(Character code) {
            this.code = code;
        }

        public Character getCode() {
            return code;
        }

        public static Gender findByCode(Character code) {
            for (Gender gender : Gender.values()) {
                if (gender.code.equals(code)) {
                    return gender;
                }
            }

            return null;
        }
    }

    public static class Database {

        public static class Schema {
            public static final String DBO = "dbo";
        }

        public static class StoredProcedure {

            public static class Name {
                public static final String CALCULATE_AVERAGE_DOSAGE = "CalculateAverageDosage";
            }

            public static class Parameters {
                public static final String TRIAL_ID = "TRIAL_ID";
            }

            public static class ResultColumn {
                public static final String TRIAL_ID = "TRIAL_ID";
                public static final String TRIAL_NAME = "TRIAL_NAME";
                public static final String DRUG_ID = "DRUG_ID";
                public static final String DRUG_NAME = "DRUG_NAME";
                public static final String DOSAGE_MEASUREMENT_TYPE = "DOSAGE_MEASUREMENT_TYPE";
                public static final String AVERAGE_DOSAGE = "AVERAGE_DOSAGE";
                public static final String PATIENT_COUNT = "PATIENT_COUNT";
            }
        }

        public static class TableName {
            public static final String PATIENT = "PATIENT";
            public static final String DRUG = "DRUG";
            public static final String TRIAL = "TRIAL";
            public static final String PATIENT_TRIAL = "PATIENT_TRIAL";
            public static final String ADVERSE_EVENT = "ADVERSE_EVENT";
            public static final String PATIENT_TRIAL_DRUG = "PATIENT_TRIAL_DRUG";
        }

        public static class ColumnName {
            public static final String ID = "ID";
            public static final String NAME = "NAME";
            public static final String DATE_OF_BIRTH = "DATE_OF_BIRTH";
            public static final String GENDER = "GENDER";
            public static final String MEDICAL_HISTORY = "MEDICAL_HISTORY";
            public static final String CHEMICAL_FORMULA = "CHEMICAL_FORMULA";
            public static final String MANUFACTURER = "MANUFACTURER";
            public static final String START_DATE = "START_DATE";
            public static final String END_DATE = "END_DATE";
            public static final String PROTOCOL_DESCRIPTION = "PROTOCOL_DESCRIPTION";
            public static final String PATIENT_ID = "PATIENT_ID";
            public static final String TRIAL_ID = "TRIAL_ID";
            public static final String ENROLLMENT_DATE = "ENROLLMENT_DATE";
            public static final String TREATMENT_STATUS = "TREATMENT_STATUS";
            public static final String PATIENT_TRIAL_ID = "PATIENT_TRIAL_ID";
            public static final String DRUG_ID = "DRUG_ID";
            public static final String DOSAGE = "DOSAGE";
            public static final String DOSAGE_MEASUREMENT_TYPE = "DOSAGE_MEASUREMENT_TYPE";
            public static final String EVENT_DESCRIPTION = "EVENT_DESCRIPTION";
            public static final String EVENT_SEVERITY = "EVENT_SEVERITY";
            public static final String DATE_OF_OCCURRENCE = "DATE_OF_OCCURRENCE";
            public static final String CREATED_AT = "CREATED_AT";
            public static final String UPDATED_AT = "UPDATED_AT";
        }
    }

}
