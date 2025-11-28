package br.com.erickmarques.loan_manager.payment;

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
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(
        name = "Payment API",
        description = "Operations related to payment management."
)
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(
            summary = "Create a new payment",
            description = "Registers a new payment linked to a loan.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Payment created successfully",
                            content = @Content(schema = @Schema(implementation = PaymentResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request payload",
                            content = @Content(schema = @Schema(implementation = ApiErrors.class))),
                    @ApiResponse(responseCode = "404", description = "Loan not found",
                            content = @Content(schema = @Schema(implementation = ApiErrors.class)))
            }
    )
    @PostMapping
    public ResponseEntity<PaymentResponse> create(@RequestBody @Validated PaymentRequest request) {
        var response = paymentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Update an existing payment",
            description = "Updates payment details using the provided ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payment updated successfully",
                            content = @Content(schema = @Schema(implementation = PaymentResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Payment or loan not found",
                            content = @Content(schema = @Schema(implementation = ApiErrors.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request payload",
                            content = @Content(schema = @Schema(implementation = ApiErrors.class)))
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> update(
            @PathVariable UUID id,
            @RequestBody @Validated PaymentRequest request
    ) {
        var response = paymentService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get a payment by ID",
            description = "Returns payment details based on the provided identifier.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payment retrieved successfully",
                            content = @Content(schema = @Schema(implementation = PaymentResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Payment not found",
                            content = @Content(schema = @Schema(implementation = ApiErrors.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> findById(@PathVariable UUID id) {
        var response = paymentService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "List all payments",
            description = "Returns an ordered list of all payments.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List returned successfully")
            }
    )
    @GetMapping
    public ResponseEntity<List<PaymentResponse>> findAll() {
        var payments = paymentService.findAll();
        return ResponseEntity.ok(payments);
    }

    @Operation(
            summary = "List all payments by Loan ID",
            description = "Returns all payments linked to the specified loan.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List returned successfully")
            }
    )
    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<PaymentResponse>> findAllByLoanId(@PathVariable UUID loanId) {
        var payments = paymentService.findAllByLoanId(loanId);
        return ResponseEntity.ok(payments);
    }

    @Operation(
            summary = "Delete a payment",
            description = "Deletes a payment using the provided ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Payment deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Payment not found",
                            content = @Content(schema = @Schema(implementation = ApiErrors.class)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        paymentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
