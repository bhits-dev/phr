package gov.samhsa.c2s.phr.domain;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Audited
public class UploadedDocument {
    @Id
    private String documentId;

    @NotNull
    private String patientMrn;

    @NotNull
    private byte[] documentContents;

    @NotNull
    private String documentFileName;

    @NotNull
    private String documentName;

    @NotNull
    private UploadedDocumentContentType documentContentType;

    private String documentDescription;
}
