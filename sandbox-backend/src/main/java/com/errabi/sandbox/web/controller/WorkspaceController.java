package com.errabi.sandbox.web.controller;

import com.errabi.sandbox.exception.ErrorResponse;
import com.errabi.sandbox.services.WorkspaceService;
import com.errabi.sandbox.web.model.WorkspaceDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<WorkspaceDto>> getAllWorkspaces(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(workspaceService.findAllWorkspaces(PageRequest.of(page,pageSize)), HttpStatus.OK);
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
