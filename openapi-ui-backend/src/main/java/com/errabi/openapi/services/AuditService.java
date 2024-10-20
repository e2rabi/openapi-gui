package com.errabi.openapi.services;

import com.errabi.openapi.entities.Audit;
import com.errabi.openapi.exception.ErrorResponse;
import com.errabi.openapi.exception.TechnicalException;
import com.errabi.openapi.repositories.AuditRepository;
import com.errabi.openapi.web.mapper.AuditMapper;
import com.errabi.openapi.web.model.AuditDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.errabi.openapi.utils.SandboxConstant.*;
import static com.errabi.openapi.utils.SandboxUtils.buildSuccessInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

    @Transactional
    public AuditDto createAudit(AuditDto auditDto){
        try {
            log.info("Creating Audit {} ..", auditDto.getUserName());
            Audit audit = auditRepository.save(auditMapper.toEntity(auditDto));
            auditDto = auditMapper.toDto(audit);
            auditDto.setResponseInfo(buildSuccessInfo());
            return auditDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the Audit");
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    SAVE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public AuditDto findAuditById(Long auditId) {
        log.info("Finding Audit with id {}",auditId);
        Optional<Audit> optionalAudit =  auditRepository.findById(auditId);
        if(optionalAudit.isPresent()){
            AuditDto auditDto = auditMapper.toDto(optionalAudit.get());
            auditDto.setResponseInfo(buildSuccessInfo());
            return auditDto;
        }else{
            log.info("Could not find module with id {}",auditId);
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    NOT_FOUND_ERROR_DESCRIPTION,
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<AuditDto> findAllAudits() {
        try {
            log.info("Fetching all Audits...");
            List<Audit> audits = auditRepository.findAll();
            if (!audits.isEmpty()) {
                return audits.stream()
                        .map(auditMapper::toDto)
                        .peek(auditDto -> auditDto.setResponseInfo(buildSuccessInfo()))
                        .toList();
            } else {
                return Collections.emptyList();
            }
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all audits");
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    SYSTEM_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public AuditDto updateAudit(AuditDto auditDto) {
        try {
            log.info("Updating Audit {} ..", auditDto.getId());
            Audit existingAudit = auditMapper.toEntity(findAuditById(auditDto.getId()));
            auditMapper.updateFromDto(auditDto, existingAudit);
            auditRepository.save(existingAudit);
            auditDto.setResponseInfo(buildSuccessInfo());
            return auditDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating audit with ID {}", auditDto.getId());
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    UPDATE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public ErrorResponse deleteAudit(Long auditId) {
        ErrorResponse errorResponse = new ErrorResponse();
        try {
            log.info("Deleting audit with ID {}", auditId);
            auditRepository.deleteById(findAuditById(auditId).getId());
            errorResponse.setResponseInfo(buildSuccessInfo());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting audit with ID {}", auditId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    DELETE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return errorResponse;
    }
}
