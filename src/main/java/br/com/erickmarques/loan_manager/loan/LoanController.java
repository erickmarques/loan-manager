package br.com.erickmarques.loan_manager.loan;

import br.com.erickmarques.loan_manager.exception.ApiErrors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
@Tag(
        name = "Loan API",
        description = "Operations related to loan management."
)
public class LoanController {

    private final LoanService loanService;

    @Operation(
            summary = "Create a new loan",
            description = "Registers a new loan linked to a specific customer.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Loan created successfully",
                            content = @Content(schema = @Schema(implementation = LoanResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request payload",
                            content = @Content(schema = @Schema(implementation = ApiErrors.class))),
                    @ApiResponse(responseCode = "404", description = "Customer not found",
                            content = @Content(schema = @Schema(implementation = ApiErrors.class)))
            }
    )
    @PostMapping
    public ResponseEntity<LoanResponse> create(@RequestBody @Validated LoanRequestCreate request) {
        var response = loanService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Update an existing loan",
            description = "Updates loan information using the provided ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Loan updated successfully",
                            content = @Content(schema = @Schema(implementation = LoanResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Loan or customer not found",
                            content = @Content(schema = @Schema(implementation = ApiErrors.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request payload",
                            content = @Content(schema = @Schema(implementation = ApiErrors.class)))
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<LoanResponse> update(
            @PathVariable UUID id,
            @RequestBody @Validated LoanRequestUpdate request
    ) {
        var response = loanService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get a loan by ID",
            description = "Returns loan details based on the provided identifier.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Loan retrieved successfully",
                            content = @Content(schema = @Schema(implementation = LoanResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Loan not found",
                            content = @Content(schema = @Schema(implementation = ApiErrors.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> findById(@PathVariable UUID id) {
        var response = loanService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "List all loans",
            description = "Returns an ordered list of all loans.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List returned successfully")
            }
    )
    @GetMapping
    public ResponseEntity<List<LoanResponse>> findAll() {
        var loans = loanService.findAll();
        return ResponseEntity.ok(loans);
    }

    @Operation(
            summary = "List all loans by Customer Id.",
            description = "Returns an ordered list of all loans.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List returned successfully")
            }
    )
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<LoanResponse>> findAllByCustomerId(@PathVariable UUID customerId) {
        var loans = loanService.findAllByCustomerId(customerId);
        return ResponseEntity.ok(loans);
    }

    @Operation(
            summary = "Delete a loan",
            description = "Deletes a loan using the provided ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Loan deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Loan not found",
                            content = @Content(schema = @Schema(implementation = ApiErrors.class)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        loanService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
