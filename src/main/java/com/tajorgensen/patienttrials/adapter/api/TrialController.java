package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.model.TrialAdverseEventListWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialDrugAverageDosageListWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialListWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialPatientListWebModel;
import com.tajorgensen.patienttrials.adapter.model.TrialWebModel;
import com.tajorgensen.patienttrials.adapter.transform.TrialAdverseEventListWebModelTransformer;
import com.tajorgensen.patienttrials.adapter.transform.TrialDrugAverageDosageListWebModelTransformer;
import com.tajorgensen.patienttrials.adapter.transform.TrialPatientListWebModelTransformer;
import com.tajorgensen.patienttrials.adapter.transform.TrialWebModelTransformer;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.InvalidRequestException;
import com.tajorgensen.patienttrials.domain.model.AdverseEvent;
import com.tajorgensen.patienttrials.domain.model.AverageDrugDosageResult;
import com.tajorgensen.patienttrials.domain.model.PatientTrial;
import com.tajorgensen.patienttrials.domain.model.Trial;
import com.tajorgensen.patienttrials.domain.usecase.CreateTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.DeleteTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetAdverseEventsForTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetAllTrialsUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetAverageDrugDosageForTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetPatientsForTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetTrialUseCase;
import com.tajorgensen.patienttrials.domain.usecase.UpdateTrialUseCase;
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

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/trials")
@Tag(name = "Trials", description = "Trial Management API")
public class TrialController {

    private final CreateTrialUseCase createTrialUseCase;

    private final GetTrialUseCase getTrialUseCase;

    private final UpdateTrialUseCase updateTrialUseCase;

    private final DeleteTrialUseCase deleteTrialUseCase;

    private final GetAllTrialsUseCase getAllTrialsUseCase;

    private final GetPatientsForTrialUseCase getPatientsForTrialUseCase;

    private final GetAverageDrugDosageForTrialUseCase getAverageDrugDosageForTrialUseCase;

    private final GetAdverseEventsForTrialUseCase getAdverseEventsForTrialUseCase;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a trial")
    public ResponseEntity<TrialWebModel> createTrial(@RequestBody TrialWebModel trialWebModel) {
        if (trialWebModel.getId() != null) {
            throw new InvalidRequestException(ErrorConstants.TrialErrorCode.INVALID_CREATE_REQUEST.getCode(), "Cannot create a trial with id provided");
        }

        Trial createdTrial = createTrialUseCase.execute(TrialWebModelTransformer.toDomain(trialWebModel));

        return ResponseEntity.status(HttpStatus.CREATED).body(TrialWebModelTransformer.fromDomain(createdTrial));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a trial based on their id")
    public TrialWebModel getTrialById(@PathVariable Long id) {
        Trial trial = getTrialUseCase.execute(id);

        return TrialWebModelTransformer.fromDomain(trial);
    }

    @GetMapping(value = "/{id}/patients", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a list of patients involved in a trial based on the trial id")
    public TrialPatientListWebModel getPatientsByTrialId(@PathVariable Long id) {
        List<PatientTrial> patientTrialList = getPatientsForTrialUseCase.execute(id);

        return TrialPatientListWebModelTransformer.fromDomain(patientTrialList);
    }

    @GetMapping(value = "/{id}/patients/adverseEvents", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a list of all the adverse events involved in a trial based on the trial id")
    public TrialAdverseEventListWebModel getAdverseEventsOfPatientsByTrialId(@PathVariable Long id) {
        List<AdverseEvent> adverseEventWebModelList = getAdverseEventsForTrialUseCase.execute(id);

        return TrialAdverseEventListWebModelTransformer.fromDomain(adverseEventWebModelList);
    }

    @GetMapping(value = "/{id}/averageDosage", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a list of drugs involved in a trial and what their average dosage is based on the trial id")
    public TrialDrugAverageDosageListWebModel getAverageDrugDosageByTrialId(@PathVariable Long id) {
        List<AverageDrugDosageResult> averageDrugDosageResultList = getAverageDrugDosageForTrialUseCase.execute(id);

        return TrialDrugAverageDosageListWebModelTransformer.fromDomain(averageDrugDosageResultList);
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update details about a trial")
    public TrialWebModel updateTrialById(@PathVariable Long id, @RequestBody TrialWebModel trialWebModel) {
        if (trialWebModel.getId() == null || id.compareTo(trialWebModel.getId()) != 0) {
            throw new InvalidRequestException(ErrorConstants.TrialErrorCode.INVALID_UPDATE_REQUEST.getCode(), "Id's do not match so update cannot be performed");
        }

        Trial trial = updateTrialUseCase.execute(TrialWebModelTransformer.toDomain(trialWebModel));

        return TrialWebModelTransformer.fromDomain(trial);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete a trial by id")
    public ResponseEntity<Void> deleteTrialById(@PathVariable Long id) {
        deleteTrialUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all trials")
    public ResponseEntity<TrialListWebModel> getAllTrials() {
        List<Trial> trialList = getAllTrialsUseCase.execute();

        List<TrialWebModel> trialWebModelList = trialList.stream().map(TrialWebModelTransformer::fromDomain).toList();

        return ResponseEntity.ok(TrialListWebModel.builder().trials(trialWebModelList).build());
    }

}
