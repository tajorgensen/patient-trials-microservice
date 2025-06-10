package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.model.PatientWebModel;
import com.tajorgensen.patienttrials.adapter.transform.PatientWebModelTransformer;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.InvalidRequestException;
import com.tajorgensen.patienttrials.domain.model.Patient;
import com.tajorgensen.patienttrials.domain.usecase.CreatePatientUseCase;
import com.tajorgensen.patienttrials.domain.usecase.DeletePatientUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetPatientUseCase;
import com.tajorgensen.patienttrials.domain.usecase.UpdatePatientUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
@Tag(name = "Patients", description = "Patient Management API")
public class PatientController {

    private final CreatePatientUseCase createPatientUseCase;

    private final GetPatientUseCase getPatientUseCase;

    private final UpdatePatientUseCase updatePatientUseCase;

    private final DeletePatientUseCase deletePatientUseCase;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a patient")
    public ResponseEntity<PatientWebModel> createPatient(@RequestBody PatientWebModel patientWebModel) {
        if (patientWebModel.getId() != null) {
            throw new InvalidRequestException(ErrorConstants.PatientErrorCode.INVALID_CREATE_REQUEST.getCode(), "Cannot create a patient with id provided");
        }

        Patient createdPatient = createPatientUseCase.execute(PatientWebModelTransformer.toDomain(patientWebModel));

        return ResponseEntity.status(HttpStatus.CREATED).body(PatientWebModelTransformer.fromDomain(createdPatient));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a patient based on their id")
    public PatientWebModel getPatientById(@PathVariable Long id) {
        Patient patient = getPatientUseCase.execute(id);

        return PatientWebModelTransformer.fromDomain(patient);
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update details about a patient")
    public PatientWebModel updatePatientById(@PathVariable Long id, @RequestBody PatientWebModel patientWebModel) {
        if (patientWebModel.getId() == null || id.compareTo(patientWebModel.getId()) != 0) {
            throw new InvalidRequestException(ErrorConstants.PatientErrorCode.INVALID_UPDATE_REQUEST.getCode(), "Id's do not match so update cannot be performed");
        }

        Patient patient = updatePatientUseCase.execute(PatientWebModelTransformer.toDomain(patientWebModel));

        return PatientWebModelTransformer.fromDomain(patient);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete a patient by id")
    public ResponseEntity<Void> deletePatientById(@PathVariable Long id) {
        deletePatientUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }

}
