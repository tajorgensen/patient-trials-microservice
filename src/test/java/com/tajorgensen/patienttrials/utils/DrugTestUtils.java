package com.tajorgensen.patienttrials.utils;

import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.DrugEntity;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientTrialDrugEntity;
import com.tajorgensen.patienttrials.adapter.model.DrugWebModel;
import com.tajorgensen.patienttrials.common.constants.ApplicationConstants;
import com.tajorgensen.patienttrials.domain.model.Drug;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DrugTestUtils {

    public static final Long ID = 1234L;
    public static final String NAME = "Ivermectin";
    private static final String CHEMICAL_FORMULA = "22,23-dihydroavermectin B1a";
    private static final String MANUFACTURER = "Stromectol";

    public static final Long PATIENT_TRIAL_DRUG_ID = 43215678L;
    public static final BigDecimal DOSAGE = new BigDecimal(12.7).setScale(2, RoundingMode.HALF_UP);
    public static final ApplicationConstants.UnitsOfMeasurement DOSAGE_MEASUREMENT_TYPE = ApplicationConstants.UnitsOfMeasurement.MILLILITERS;

    public static void assertWebModelsEqual(DrugWebModel expected, DrugWebModel actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getChemicalFormula(), actual.getChemicalFormula()),
                () -> assertEquals(expected.getManufacturer(), actual.getManufacturer())
        );
    }

    public static void assertWebModelListsAreEqual(List<DrugWebModel> expectedList, List<DrugWebModel> actualList) {
        if (expectedList == null) {
            assertNull(actualList);
            return;
        }

        assertNotNull(actualList);
        assertEquals(expectedList.size(), actualList.size());

        for (int i = 0; i < expectedList.size(); i++) {
            assertWebModelsEqual(expectedList.get(i), actualList.get(i));
        }
    }

    public static void assertDomainModelsEqual(Drug expected, Drug actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getChemicalFormula(), actual.getChemicalFormula()),
                () -> assertEquals(expected.getManufacturer(), actual.getManufacturer())
        );
    }

    public static void assertDomainModelListsAreEqual(List<Drug> expectedList, List<Drug> actualList) {
        if (expectedList == null) {
            assertNull(actualList);
            return;
        }

        assertNotNull(actualList);
        assertEquals(expectedList.size(), actualList.size());

        for (int i = 0; i < expectedList.size(); i++) {
            assertDomainModelsEqual(expectedList.get(i), actualList.get(i));
        }
    }

    public static void assertEntitiesEqual(DrugEntity expected, DrugEntity actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getChemicalFormula(), actual.getChemicalFormula()),
                () -> assertEquals(expected.getManufacturer(), actual.getManufacturer())
        );
    }

    public static void assertEntitiesEqual(PatientTrialDrugEntity expected, PatientTrialDrugEntity actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getDosage(), actual.getDosage()),
                () -> assertEquals(expected.getDosageMeasurementType(), actual.getDosageMeasurementType()),
                () -> assertEquals(expected.getDrugId(), actual.getDrugId()),
                () -> assertEquals(expected.getPatientTrialId(), actual.getPatientTrialId())
        );
    }

    public static DrugWebModel createWebModel() {
        return DrugWebModel.builder()
                .id(ID)
                .name(NAME)
                .chemicalFormula(CHEMICAL_FORMULA)
                .manufacturer(MANUFACTURER)
                .build();
    }

    public static Drug createDomain() {
        return Drug.builder()
                .id(ID)
                .name(NAME)
                .chemicalFormula(CHEMICAL_FORMULA)
                .manufacturer(MANUFACTURER)
                .build();
    }

    public static DrugEntity createEntity() {
        return DrugEntity.builder()
                .id(ID)
                .name(NAME)
                .chemicalFormula(CHEMICAL_FORMULA)
                .manufacturer(MANUFACTURER)
                .build();
    }

    public static PatientTrialDrugEntity createPatientTrialDrugEntity() {
        return PatientTrialDrugEntity.builder()
                .id(PATIENT_TRIAL_DRUG_ID)
                .drugId(ID)
                .patientTrialId(PatientTrialTestUtils.ID)
                .dosage(DOSAGE)
                .dosageMeasurementType(DOSAGE_MEASUREMENT_TYPE.name())
                .build();
    }

    public static Drug createDrugFromPatientTrialDrug() {
        return Drug.builder()
                .id(ID)
                .name(NAME)
                .chemicalFormula(CHEMICAL_FORMULA)
                .manufacturer(MANUFACTURER)
                .dosage(DOSAGE)
                .dosageMeasurementType(DOSAGE_MEASUREMENT_TYPE)
                .build();
    }

}
