package com.tajorgensen.patienttrials.adapter.api;

import com.tajorgensen.patienttrials.adapter.model.DrugWebModel;
import com.tajorgensen.patienttrials.adapter.transform.DrugWebModelTransformer;
import com.tajorgensen.patienttrials.common.exception.ErrorConstants;
import com.tajorgensen.patienttrials.common.exception.InvalidRequestException;
import com.tajorgensen.patienttrials.domain.model.Drug;
import com.tajorgensen.patienttrials.domain.usecase.CreateDrugUseCase;
import com.tajorgensen.patienttrials.domain.usecase.DeleteDrugUseCase;
import com.tajorgensen.patienttrials.domain.usecase.GetDrugUseCase;
import com.tajorgensen.patienttrials.domain.usecase.UpdateDrugUseCase;
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
@RequestMapping("/drugs")
@Tag(name = "Drugs", description = "Drug Management API")
public class DrugController {

    private final CreateDrugUseCase createDrugUseCase;

    private final GetDrugUseCase getDrugUseCase;

    private final UpdateDrugUseCase updateDrugUseCase;

    private final DeleteDrugUseCase deleteDrugUseCase;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a drug")
    public ResponseEntity<DrugWebModel> createDrug(@RequestBody DrugWebModel drugWebModel) {
        if (drugWebModel.getId() != null) {
            throw new InvalidRequestException(ErrorConstants.DrugErrorCode.INVALID_CREATE_REQUEST.getCode(), "Cannot create a drug with id provided");
        }

        Drug createdDrug = createDrugUseCase.execute(DrugWebModelTransformer.toDomain(drugWebModel));

        return ResponseEntity.status(HttpStatus.CREATED).body(DrugWebModelTransformer.fromDomain(createdDrug));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a drug based on their id")
    public DrugWebModel getDrugById(@PathVariable Long id) {
        Drug drug = getDrugUseCase.execute(id);

        return DrugWebModelTransformer.fromDomain(drug);
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update details about a drug")
    public DrugWebModel updateDrugById(@PathVariable Long id, @RequestBody DrugWebModel drugWebModel) {
        if (drugWebModel.getId() == null || id.compareTo(drugWebModel.getId()) != 0) {
            throw new InvalidRequestException(ErrorConstants.DrugErrorCode.INVALID_UPDATE_REQUEST.getCode(), "Id's do not match so update cannot be performed");
        }

        Drug drug = updateDrugUseCase.execute(DrugWebModelTransformer.toDomain(drugWebModel));

        return DrugWebModelTransformer.fromDomain(drug);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete a drug by id")
    public ResponseEntity<Void> deleteDrugById(@PathVariable Long id) {
        deleteDrugUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }

}
