package gov.samhsa.c2s.phr.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "c2s.phr")
@Validated
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhrProperties {
    @Valid
    private PatientDocumentUploads patientDocumentUploads;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PatientDocumentUploads {
        @NotEmpty
        private List<String> extensionsPermittedToUpload = new ArrayList<>();

        public List<String> getExtensionsPermittedToUpload() {
            return extensionsPermittedToUpload;
        }
    }
}
