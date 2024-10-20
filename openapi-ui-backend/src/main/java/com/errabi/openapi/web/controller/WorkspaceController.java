package com.errabi.openapi.web.controller;

import com.errabi.openapi.exception.ErrorResponse;
import com.errabi.openapi.services.WorkspaceService;
import com.errabi.openapi.web.model.WorkspaceDto;
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

    @GetMapping("/workspaces/query")
    public ResponseEntity<Page<WorkspaceDto>> getWorkspacesByFilter(@RequestParam(required = false) String status,
                                                          @RequestParam(required = false) String visibility,
                                                          @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
        return new ResponseEntity<>(workspaceService.findWorkspacesByFilter(status,visibility,PageRequest.of(page,pageSize)), HttpStatus.OK);
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
