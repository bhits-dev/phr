package gov.samhsa.c2s.phr.service.dto;

import gov.samhsa.c2s.phr.domain.UploadedDocumentContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadedDocumentDto {
    @NotEmpty
    private String documentId;

    @NotEmpty
    private String patientMrn;

    @NotEmpty
    private byte[] documentContents;

    @NotEmpty
    private String documentFileName;

    @NotEmpty
    private String documentName;

    @Valid
    @NotEmpty
    private UploadedDocumentContentType documentContentType;

    private String documentDescription;
}
