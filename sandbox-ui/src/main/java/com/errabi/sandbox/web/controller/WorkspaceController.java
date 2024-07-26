package com.errabi.sandbox.web.controller;

import com.errabi.sandbox.exception.ErrorResponse;
import com.errabi.sandbox.services.WorkspaceService;
import com.errabi.sandbox.web.model.WorkspaceDto;
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
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    @PostMapping("/workspace")
    public ResponseEntity<WorkspaceDto> createWorkspace(@RequestBody @Valid WorkspaceDto workspaceDto){
        return new ResponseEntity<>(workspaceService.createWorkspace(workspaceDto), HttpStatus.CREATED);
    }

    @GetMapping("/workspace/{workspaceId}")
    @Cacheable(value = "sandbox", key = "#workspaceId")
    public ResponseEntity<WorkspaceDto> getWorkspaceById(@PathVariable  Long workspaceId){
        return new ResponseEntity<>(workspaceService.findWorkspaceById(workspaceId), HttpStatus.OK);
    }

    @GetMapping("/workspaces")
    public ResponseEntity<List<WorkspaceDto>> getAllWorkspaces() {
        List<WorkspaceDto> workspaceDto = workspaceService.findAllWorkspaces();
        return new ResponseEntity<>(workspaceDto, HttpStatus.OK);
    }

    @PutMapping("/workspace")
    public ResponseEntity<WorkspaceDto> updateWorkspace(@RequestBody @Valid WorkspaceDto workspaceDto) {
        return new ResponseEntity<>(workspaceService.updateWorkspace(workspaceDto), HttpStatus.OK);
    }

    @DeleteMapping("/workspace/{workspaceId}")
    public ResponseEntity<ErrorResponse> deleteWorkspace(@PathVariable Long workspaceId){
        return new ResponseEntity<>(workspaceService.deleteWorkspace(workspaceId), HttpStatus.OK);
    }
}
