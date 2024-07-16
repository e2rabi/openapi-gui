package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Audit;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.AuditRepository;
import com.errabi.sandbox.web.mapper.AuditMapper;
import com.errabi.sandbox.web.model.AuditDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.errabi.sandbox.utils.SandboxConstant.*;

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
            Audit audit = auditMapper.toEntity(auditDto);
            auditRepository.save(audit);
            return auditDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the Audit", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    "Unexpected error occurred while saving the Audit",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public AuditDto findAuditById(Long auditId) {
        log.info("Finding Audit with id {}",auditId);
        Optional<Audit> optionalAudit =  auditRepository.findById(auditId);
        if(optionalAudit.isPresent()){
            return auditMapper.toDto(optionalAudit.get());
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No Audit found",
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<AuditDto> findAllAudits() {
        try {
            log.info("Fetching all Audits...");
            List<Audit> audits = auditRepository.findAll();
            return audits.stream().map(auditMapper::toDto).toList();
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all audits", ex);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    "Unexpected error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public AuditDto updateAudit(AuditDto auditDto) {
        try {
            log.info("Updating Audit {} ..", auditDto.getId());
            Audit existingAudit = auditMapper.toEntity(findAuditById(auditDto.getId()));
            auditMapper.updateFromDto(auditDto, existingAudit);
            Audit updatedAudit = auditRepository.save(existingAudit);
            return auditMapper.toDto(updatedAudit);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating audit with ID {}", auditDto.getId());
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    "Unexpected error occurred while updating audit",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public void deleteAudit(Long auditId) {
        try {
            log.info("Deleting audit with ID {}", auditId);
            auditRepository.deleteById(findAuditById(auditId).getId());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting audit with ID {}", auditId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    "Unexpected error occurred while deleting the audit",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
