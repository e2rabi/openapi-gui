package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Workspace;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.WorkspaceRepository;
import com.errabi.sandbox.web.mapper.WorkspaceMapper;
import com.errabi.sandbox.web.model.WorkspaceDto;
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
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMapper workspaceMapper;

    @Transactional
    public WorkspaceDto createWorkspace(WorkspaceDto workspaceDto){
        try {
            log.info("Creating Workspace {} ..", workspaceDto.getName());
            Workspace workspace = workspaceMapper.toEntity(workspaceDto);
            workspaceRepository.save(workspace);
            return workspaceDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the Workspace", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    "Unexpected error occurred while saving the Workspace",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public WorkspaceDto findWorkspaceById(Long workspaceId) {
        log.info("Finding Workspace with id {}",workspaceId);
        Optional<Workspace> optionalWorkspace =  workspaceRepository.findById(workspaceId);
        if(optionalWorkspace.isPresent()){
            return workspaceMapper.toDto(optionalWorkspace.get());
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No Workspace found",
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<WorkspaceDto> findAllWorkspaces() {
        try {
            log.info("Fetching all Workspaces...");
            List<Workspace> workspaces = workspaceRepository.findAll();
            return workspaces.stream().map(workspaceMapper::toDto).toList();
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all Workspaces", ex);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    "Unexpected error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public WorkspaceDto updateWorkspace(WorkspaceDto workspaceDto) {
        try {
            log.info("Updating Workspace {} ..", workspaceDto.getId());
            Workspace existingWorkspace = workspaceMapper.toEntity(findWorkspaceById(workspaceDto.getId()));
            workspaceMapper.updateFromDto(workspaceDto, existingWorkspace);
            Workspace updatedWorkspace = workspaceRepository.save(existingWorkspace);
            return workspaceMapper.toDto(updatedWorkspace);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating Workspace with ID {}", workspaceDto.getId());
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    "Unexpected error occurred while updating Workspace",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public void deleteWorkspace(Long workspaceId) {
        try {
            log.info("Deleting Workspace with ID {}", workspaceId);
            workspaceRepository.deleteById(findWorkspaceById(workspaceId).getId());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting Workspace with ID {}", workspaceId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    "Unexpected error occurred while deleting the Workspace",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
