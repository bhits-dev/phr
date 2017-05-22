package gov.samhsa.c2s.phr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class UploadedDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String patientMrn;

    @Lob
    @NotEmpty
    private byte[] contents;

    @NotEmpty
    private String fileName;

    @NotEmpty
    private String documentName;

    @NotEmpty
    private String contentType;

    private String description;

    @NotNull
    @ManyToOne
    @JsonIgnore
    private DocumentTypeCode documentTypeCode;
}
