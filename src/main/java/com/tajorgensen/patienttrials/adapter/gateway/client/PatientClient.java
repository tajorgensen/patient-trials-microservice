package com.tajorgensen.patienttrials.adapter.gateway.client;

import com.tajorgensen.patienttrials.adapter.gateway.repository.PatientRepository;
import com.tajorgensen.patienttrials.adapter.gateway.repository.entity.PatientEntity;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.ResourceNotFoundException;
import com.tajorgensen.patienttrials.domain.model.Patient;
import com.tajorgensen.patienttrials.domain.port.PatientPort;
import lombok.AllArgsConstructor;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class PatientClient implements PatientPort {

    private PatientRepository patientRepository;

    @Override
    public Patient createPatient(Patient patient) {
        PatientEntity patientEntity = patientRepository.save(PatientEntity.of(patient));
        return patientEntity.toDomain();
    }

    @Override
    public Patient getPatientById(Long id) {
        Optional<PatientEntity> patientEntity = patientRepository.findById(id);

        if (patientEntity.isEmpty()) {
            throw new ResourceNotFoundException(ErrorConstants.PatientErrorCode.GET_ID_NOT_FOUND.getCode(), String.format("Unable to find patient with id of %d", id));
        }

        return patientEntity.get().toDomain();
    }

    @Override
    public void deletePatientById(Long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public Patient updatePatient(Patient patient) {
        try {
            PatientEntity patientEntity = patientRepository.saveIfExists(PatientEntity.of(patient));
            return patientEntity.toDomain();
        } catch (JpaObjectRetrievalFailureException ex) {
            throw new ResourceNotFoundException(ErrorConstants.PatientErrorCode.UPDATE_ID_NOT_FOUND.getCode(), String.format("Unable to find patient with id of %d", patient.getId()));
        }
    }
}
