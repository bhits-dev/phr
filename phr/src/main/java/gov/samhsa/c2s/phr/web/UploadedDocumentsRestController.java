package gov.samhsa.c2s.phr.web;

import gov.samhsa.c2s.phr.service.FileCheckService;
import gov.samhsa.c2s.phr.service.UploadedDocumentService;
import gov.samhsa.c2s.phr.service.dto.SaveNewUploadedDocumentDto;
import gov.samhsa.c2s.phr.service.dto.SavedNewUploadedDocumentResponseDto;
import gov.samhsa.c2s.phr.service.dto.UploadedDocumentDto;
import gov.samhsa.c2s.phr.service.dto.UploadedDocumentInfoDto;
import gov.samhsa.c2s.phr.service.exception.DocumentSaveException;
import gov.samhsa.c2s.phr.service.exception.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/uploadedDocuments")
public class UploadedDocumentsRestController {
    private final UploadedDocumentService uploadedDocumentService;
    private final FileCheckService fileCheckService;

    @Autowired
    public UploadedDocumentsRestController(UploadedDocumentService uploadedDocumentService, FileCheckService fileCheckService) {
        this.uploadedDocumentService = uploadedDocumentService;
        this.fileCheckService = fileCheckService;
    }

    @GetMapping("/patient/{patientMrn}/documentsList")
    public List<UploadedDocumentInfoDto> getPatientDocumentsList(@PathVariable String patientMrn){
        return uploadedDocumentService.getPatientDocumentInfoList(patientMrn);
    }

    @GetMapping("/patient/{patientMrn}/document/{documentId}")
    public UploadedDocumentDto getPatientDocument(@PathVariable String patientMrn, @PathVariable Long documentId){
        return uploadedDocumentService.getPatientDocumentByDocId(patientMrn, documentId);
    }

    /**
     * Saves new uploaded patient document file
     *
     * @param patientMrn - the MRN of the patient for whom the uploaded file belongs to
     * @param file - the file to be saved
     * @param documentName - the user chosen name of the file being uploaded (this may or may not be identical to the documentFileName)
     * @param documentDescription - An optional description of the file being uploaded
     * @return An object containing metadata about the newly saved patient document;
     *         - if the specified patient already has document saved with the same
     *           documentName, an HTTP 409 - CONFLICT status code will be returned.
     * @see SavedNewUploadedDocumentResponseDto
     */
    @PostMapping("/patient/{patientMrn}/document")
    public SavedNewUploadedDocumentResponseDto saveNewPatientDocument(@PathVariable String patientMrn,
                                                                      @RequestParam("file") MultipartFile file,
                                                                      @RequestParam("documentName") String documentName,
                                                                      @RequestParam("documentDescription") String documentDescription){

        // TODO: Invoke ClamAV scanner service to scan uploaded file for viruses before doing anything else.

        boolean isFileOversized = fileCheckService.isFileOversized(file);
        if(isFileOversized){
            log.error("The uploaded file (filename: " + file.getOriginalFilename() + ") was not saved because the file size was greater than the configured maximum file size" );
            throw new InvalidInputException("The uploaded file could not be saved because the file size was too large");
        }

        if(!fileCheckService.isFileExtensionPermitted(file)){
            log.error("The uploaded file (filename: " + file.getOriginalFilename() + ") was not saved because the file extension is not a permitted extension type");
            throw new InvalidInputException("The uploaded file could not be saved because the file extension was not a permitted extension type");
        }

        SaveNewUploadedDocumentDto saveNewUploadedDocumentDto;
        byte[] uploadedFileBytes;

        try{
            uploadedFileBytes = file.getBytes();
        }catch (IOException e){
            log.error("An IOException occurred while invoking file.getBytes from inside the saveNewPatientDocument controller method", e);
            throw new DocumentSaveException("An error occurred while attempting to save a new document", e);
        }

        if(uploadedFileBytes.length <= 0){
            log.error("The byte array extracted from the uploaded MultipartFile object was empty (uploadedFileBytes.length: " + uploadedFileBytes.length + ")");
            throw new InvalidInputException("The uploaded file was empty");
        }

        saveNewUploadedDocumentDto = new SaveNewUploadedDocumentDto(patientMrn, uploadedFileBytes, file.getOriginalFilename(), documentName, file.getContentType(), documentDescription);

        return uploadedDocumentService.saveNewPatientDocument(saveNewUploadedDocumentDto);
    }

}
