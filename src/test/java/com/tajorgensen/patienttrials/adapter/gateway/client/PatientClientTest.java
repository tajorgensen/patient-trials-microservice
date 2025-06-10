package com.tajorgensen.patienttrials.adapter.gateway.client;

import com.tajorgensen.patienttrials.adapter.gateway.repository.PatientRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientEntity;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.model.Patient;
import com.tajorgensen.patienttrials.utils.PatientTestUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientClientTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientClient patientClient;

    @Test
    void testCreatePatient() {
        PatientEntity patientEntity = PatientTestUtils.createEntity();

        when(patientRepository.save(eq(patientEntity))).thenReturn(patientEntity);

        Patient patient = PatientTestUtils.createDomain();

        Patient actualResult = patientClient.createPatient(patient);

        PatientTestUtils.assertDomainModelsEqual(patient, actualResult);
    }

    @Test
    void testGetPatientById() {
        PatientEntity patientEntity = PatientTestUtils.createEntity();

        when(patientRepository.findById(eq(PatientTestUtils.ID))).thenReturn(Optional.of(patientEntity));

        Patient expectedResult = PatientTestUtils.createDomain();

        Patient actualResult = patientClient.getPatientById(PatientTestUtils.ID);

        PatientTestUtils.assertDomainModelsEqual(expectedResult, actualResult);
    }

    @Test
    void testGetPatientById_DoesNotExist() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> patientClient.getPatientById(PatientTestUtils.ID));

        assertEquals(ErrorConstants.PatientErrorCode.GET_ID_NOT_FOUND.getCode(), ex.getErrorDetails().getErrorCode());
    }

    @Test
    void testUpdatePatient() {
        PatientEntity patientEntity = PatientTestUtils.createEntity();

        when(patientRepository.saveIfExists(eq(patientEntity))).thenReturn(patientEntity);

        Patient patient = PatientTestUtils.createDomain();

        Patient actualResult = patientClient.updatePatient(patient);

        PatientTestUtils.assertDomainModelsEqual(patient, actualResult);
    }

    @Test
    void testUpdatePatient_DoesNotExist() {
        PatientEntity patientEntity = PatientTestUtils.createEntity();
        when(patientRepository.saveIfExists(eq(patientEntity))).thenThrow(new JpaObjectRetrievalFailureException(new EntityNotFoundException()));

        Patient patient = PatientTestUtils.createDomain();

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> patientClient.updatePatient(patient));

        assertEquals(ErrorConstants.PatientErrorCode.UPDATE_ID_NOT_FOUND.getCode(), ex.getErrorDetails().getErrorCode());
    }

    @Test
    void testDeletePatient() {
        assertDoesNotThrow(() -> patientClient.deletePatientById(PatientTestUtils.ID));
        verify(patientRepository, times(1)).deleteById(PatientTestUtils.ID);
    }

    @Test
    void testDeletePatient_DoesNotExist() {
        doNothing().when(patientRepository).deleteById(eq(PatientTestUtils.ID));
        assertDoesNotThrow(() -> patientClient.deletePatientById(PatientTestUtils.ID));
    }

}