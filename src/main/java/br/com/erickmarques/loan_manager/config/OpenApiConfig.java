package br.com.erickmarques.loan_manager.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI loanManagerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Loan Manager API")
                        .description("API documentation for the Loan Manager system")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Repository")
                        .url("https://github.com/your-repo-url"));
    }
}
