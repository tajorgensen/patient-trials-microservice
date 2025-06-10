package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.model.PatientTrialWebModel;
import com.tajorgensen.patienttrials.adapter.transform.PatientTrialWebModelTransformer;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.InvalidRequestException;
import com.tajorgensen.patienttrials.domain.model.PatientTrial;
import com.tajorgensen.patienttrials.domain.usecase.CreatePatientTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.DeletePatientTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetPatientTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.UpdatePatientTrialUseCase;
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
@RequestMapping("/patientTrials")
@Tag(name = "Patient Trials", description = "Patient Trial Management API")
public class PatientTrialController {

    private final CreatePatientTrialUseCase createPatientTrialUseCase;

    private final GetPatientTrialUseCase getPatientTrialUseCase;

    private final UpdatePatientTrialUseCase updatePatientTrialUseCase;

    private final DeletePatientTrialUseCase deletePatientTrialUseCase;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a Patient Trial assignment")
    public ResponseEntity<PatientTrialWebModel> createPatientTrial(@RequestBody PatientTrialWebModel patientTrialWebModel) {
        if (patientTrialWebModel.getId() != null) {
            throw new InvalidRequestException(ErrorConstants.PatientTrialErrorCode.INVALID_CREATE_REQUEST.getCode(), "Cannot create a Patient Trial assignment with id provided");
        }

        PatientTrial createdPatientTrial = createPatientTrialUseCase.execute(PatientTrialWebModelTransformer.toDomain(patientTrialWebModel));

        return ResponseEntity.status(HttpStatus.CREATED).body(PatientTrialWebModelTransformer.fromDomain(createdPatientTrial));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a Patient Trial assignment based on their id")
    public PatientTrialWebModel getPatientTrialById(@PathVariable Long id) {
        PatientTrial patientTrial = getPatientTrialUseCase.execute(id);

        return PatientTrialWebModelTransformer.fromDomain(patientTrial);
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update details about a assignment Patient trial")
    public PatientTrialWebModel updatePatientTrialById(@PathVariable Long id, @RequestBody PatientTrialWebModel patientTrialWebModel) {
        if (patientTrialWebModel.getId() == null || id.compareTo(patientTrialWebModel.getId()) != 0) {
            throw new InvalidRequestException(ErrorConstants.PatientTrialErrorCode.INVALID_UPDATE_REQUEST.getCode(), "Id's do not match so update cannot be performed");
        }

        PatientTrial patientTrial = updatePatientTrialUseCase.execute(PatientTrialWebModelTransformer.toDomain(patientTrialWebModel));

        return PatientTrialWebModelTransformer.fromDomain(patientTrial);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete a Patient Trial assignment by id")
    public ResponseEntity<Void> deletePatientTrialById(@PathVariable Long id) {
        deletePatientTrialUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }

}
