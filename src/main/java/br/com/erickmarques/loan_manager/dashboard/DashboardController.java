package br.com.erickmarques.loan_manager.dashboard;

import br.com.erickmarques.loan_manager.loan.LoanResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Tag(
        name = "Dashboard API",
        description = "Operations related to Dashboard."
)
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(
            summary = "Get a total amount loaned for data",
            description = "Returns the total amount loaned based on a date, by default the last 30 days.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Loan retrieved successfully",
                            content = @Content(schema = @Schema(implementation = LoanResponse.class)))
            }
    )
    @GetMapping("/summary")
    public ResponseEntity<SummaryResponse> getSummaryForDate(@RequestParam(required = false) LocalDate date) {
        return ResponseEntity.ok(dashboardService.getSummaryForDate(date));
    }

    @GetMapping("/summary-total")
    public ResponseEntity<SummaryTotalResponse> getSummaryTotal() {
        return ResponseEntity.ok(dashboardService.getSummaryTotal());
    }

    @GetMapping("/received-type")
    public ResponseEntity<ReceivedByTypeResponse> getSummaryReceivedByType(@RequestParam(required = false) LocalDate date) {
        return ResponseEntity.ok(dashboardService.getSummaryReceivedByType(date));
    }
}
