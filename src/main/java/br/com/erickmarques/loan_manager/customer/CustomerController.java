package br.com.erickmarques.loan_manager.customer;

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
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(
        name = "Customer API",
        description = "Operations related to customer management."
)
public class CustomerController {

    private final CustomerService customerService;

    @Operation(
            summary = "Create a new customer",
            description = "Registers a new customer in the system.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Customer created successfully",
                            content = @Content(schema = @Schema(implementation = CustomerResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request payload",
                            content = @Content(schema = @Schema(implementation = ApiErrors.class)))
            }
    )
    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody @Validated CustomerRequest request) {
        var response = customerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Update an existing customer",
            description = "Updates customer information using the provided ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer updated successfully",
                            content = @Content(schema = @Schema(implementation = CustomerResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Customer not found",
                            content = @Content(schema = @Schema(implementation = ApiErrors.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request payload",
                            content = @Content(schema = @Schema(implementation = ApiErrors.class)))
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(
            @PathVariable UUID id,
            @RequestBody @Validated CustomerRequest request
    ) {
        var response = customerService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get a customer by ID",
            description = "Returns customer information based on the provided identifier.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer retrieved",
                            content = @Content(schema = @Schema(implementation = CustomerResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Customer not found",
                            content = @Content(schema = @Schema(implementation = ApiErrors.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable UUID id) {
        var response = customerService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "List all customers",
            description = "Returns an ordered list of all customers.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List returned successfully")
            }
    )
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll() {
        var customers = customerService.findAll();
        return ResponseEntity.ok(customers);
    }

    @Operation(
            summary = "Delete a customer",
            description = "Deletes a customer using the provided ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Customer not found",
                            content = @Content(schema = @Schema(implementation = ApiErrors.class)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
