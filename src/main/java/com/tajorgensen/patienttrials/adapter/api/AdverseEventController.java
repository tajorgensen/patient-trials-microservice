package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.model.AdverseEventWebModel;
import com.tajorgensen.patienttrials.adapter.transform.AdverseEventWebModelTransformer;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.InvalidRequestException;
import com.tajorgensen.patienttrials.domain.model.AdverseEvent;
import com.tajorgensen.patienttrials.domain.usecase.CreateAdverseEventUseCase;
import com.tajorgensen.patienttrials.domain.usecase.DeleteAdverseEventUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetAdverseEventUseCase;
import com.tajorgensen.patienttrials.domain.usecase.UpdateAdverseEventUseCase;
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
@RequestMapping("/adverseEvents")
@Tag(name = "Adverse Events", description = "Adverse Event Management API")
public class AdverseEventController {

    private final CreateAdverseEventUseCase createAdverseEventUseCase;

    private final GetAdverseEventUseCase getAdverseEventUseCase;

    private final UpdateAdverseEventUseCase updateAdverseEventUseCase;

    private final DeleteAdverseEventUseCase deleteAdverseEventUseCase;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create an Adverse Event")
    public ResponseEntity<AdverseEventWebModel> createAdverseEvent(@RequestBody AdverseEventWebModel adverseEventWebModel) {
        if (adverseEventWebModel.getId() != null) {
            throw new InvalidRequestException(ErrorConstants.AdverseEventErrorCode.INVALID_CREATE_REQUEST.getCode(), "Cannot create a adverseEvent with id provided");
        }

        AdverseEvent createdAdverseEvent = createAdverseEventUseCase.execute(AdverseEventWebModelTransformer.toDomain(adverseEventWebModel));

        return ResponseEntity.status(HttpStatus.CREATED).body(AdverseEventWebModelTransformer.fromDomain(createdAdverseEvent));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get an Adverse Event based on their id")
    public AdverseEventWebModel getAdverseEventById(@PathVariable Long id) {
        AdverseEvent adverseEvent = getAdverseEventUseCase.execute(id);

        return AdverseEventWebModelTransformer.fromDomain(adverseEvent);
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update details about an Adverse Event")
    public AdverseEventWebModel updateAdverseEventById(@PathVariable Long id, @RequestBody AdverseEventWebModel adverseEventWebModel) {
        if (adverseEventWebModel.getId() == null || id.compareTo(adverseEventWebModel.getId()) != 0) {
            throw new InvalidRequestException(ErrorConstants.AdverseEventErrorCode.INVALID_UPDATE_REQUEST.getCode(), "Id's do not match so update cannot be performed");
        }

        AdverseEvent adverseEvent = updateAdverseEventUseCase.execute(AdverseEventWebModelTransformer.toDomain(adverseEventWebModel));

        return AdverseEventWebModelTransformer.fromDomain(adverseEvent);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete an Adverse Event by id")
    public ResponseEntity<Void> deleteAdverseEventById(@PathVariable Long id) {
        deleteAdverseEventUseCase.execute(id);

        return ResponseEntity.ok().build();
    }

}
