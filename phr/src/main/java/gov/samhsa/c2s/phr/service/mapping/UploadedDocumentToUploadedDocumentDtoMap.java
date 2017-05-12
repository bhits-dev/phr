package gov.samhsa.c2s.phr.service.mapping;

import gov.samhsa.c2s.phr.domain.UploadedDocument;
import gov.samhsa.c2s.phr.service.dto.UploadedDocumentDto;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class UploadedDocumentToUploadedDocumentDtoMap extends PropertyMap<UploadedDocument, UploadedDocumentDto> {
    @Override
    protected void configure() {
        map().setDocumentId(source.getDocumentId());
        map().setDocumentFileName(source.getDocumentFileName());
        map().setDocumentName(source.getDocumentName());
        map().setDocumentContentType(source.getDocumentContentType());
        map().setDocumentDescription(source.getDocumentDescription());
        map().setDocumentContents(source.getDocumentContents());
        map().setPatientMrn(source.getPatientMrn());
    }
}
