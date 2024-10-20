package com.errabi.openapi.web.controller;

import com.errabi.openapi.exception.ErrorResponse;
import com.errabi.openapi.services.AuditService;
import com.errabi.openapi.web.model.AuditDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sandbox-api/v1/")
@RequiredArgsConstructor
public class AuditController {
    private final AuditService auditService;

    @PostMapping("/audits")
    public ResponseEntity<AuditDto> createAudit(@RequestBody @Valid AuditDto auditDto){
        return new ResponseEntity<>(auditService.createAudit(auditDto), HttpStatus.CREATED);
    }

    @GetMapping("/audits/{auditId}")
    @Cacheable(value = "sandbox", key = "#auditId")
    public ResponseEntity<AuditDto> getAuditById(@PathVariable  Long auditId){
        return new ResponseEntity<>(auditService.findAuditById(auditId), HttpStatus.OK);
    }

    @GetMapping("/audits")
    public ResponseEntity<List<AuditDto>> getAllAudits() {
        List<AuditDto> auditsDto = auditService.findAllAudits();
        return new ResponseEntity<>(auditsDto, HttpStatus.OK);
    }

    @PutMapping("/audits")
    public ResponseEntity<AuditDto> updateAudit(@RequestBody @Valid AuditDto auditDto) {
        return new ResponseEntity<>(auditService.updateAudit(auditDto), HttpStatus.OK);
    }

    @DeleteMapping("/audits/{auditId}")
    public ResponseEntity<ErrorResponse> deleteAudit(@PathVariable Long auditId){
        return new ResponseEntity<>(auditService.deleteAudit(auditId), HttpStatus.OK);
    }
}
