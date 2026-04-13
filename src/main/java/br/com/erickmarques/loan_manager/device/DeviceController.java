package br.com.erickmarques.loan_manager.device;

import br.com.erickmarques.loan_manager.secutiry.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping
    public void saveToken(@RequestBody @Validated DeviceTokenRequest request,
                          Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        deviceService.save(request, user.getId());
    }
}