package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Workspace;
import com.errabi.sandbox.exception.ErrorResponse;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.WorkspaceRepository;
import com.errabi.sandbox.web.mapper.WorkspaceMapper;
import com.errabi.sandbox.web.model.WorkspaceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.errabi.sandbox.utils.SandboxConstant.*;
import static com.errabi.sandbox.utils.SandboxUtils.buildSuccessInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMapper workspaceMapper;
    private final UserService userService;

    @Transactional
    public WorkspaceDto createWorkspace(WorkspaceDto workspaceDto){
        try {
            log.info("Creating Workspace {} ..", workspaceDto.getName());
            Workspace workspace = workspaceRepository.save(workspaceMapper.toEntity(workspaceDto));
            workspaceDto = workspaceMapper.toDto(workspace);
            workspaceDto.setResponseInfo(buildSuccessInfo());
            return workspaceDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the Workspace");
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    SAVE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public WorkspaceDto findWorkspaceById(Long workspaceId) {
        log.info("Finding Workspace with id {}",workspaceId);
        Optional<Workspace> optionalWorkspace =  workspaceRepository.findById(workspaceId);
        if(optionalWorkspace.isPresent()){
            WorkspaceDto workspaceDto = workspaceMapper.toDto(optionalWorkspace.get());
            workspaceDto.setResponseInfo(buildSuccessInfo());
            return workspaceDto;
        }else{
            log.error("Unexpected error occurred while finding the Workspace");
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    NOT_FOUND_ERROR_DESCRIPTION,
                    HttpStatus.NOT_FOUND);
        }
    }

    public Page<WorkspaceDto> findWorkspacesByFilter(String status, String visibility, Pageable pageable) {

        Workspace workspaceProb = Workspace.builder()
                .enabled("all".equalsIgnoreCase(status)?null: !"inactive".equalsIgnoreCase(status))
                .visibility(StringUtils.isEmpty(visibility)?null:Boolean.valueOf(visibility))
                .build();

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id", "version");

        Example<Workspace> example = Example.of(workspaceProb, matcher);

        return workspaceRepository.findAll(example, pageable).map(workspaceMapper::toDto);
    }

    public Page<WorkspaceDto> findAllWorkspaces(Pageable pageable) {
        try {
            log.info("Fetching all Workspaces...");
            Page<Workspace> workspaces = workspaceRepository.findAll(pageable);
            if (!workspaces.isEmpty()) {
                return workspaces.map(workspace -> {
                    WorkspaceDto workspaceDto = workspaceMapper.toDto(workspace);
                    long numberOfUsers = userService.getNumberOfUsersInWorkspace(workspace.getId());
                    workspaceDto.setNbOfUsers(numberOfUsers);
                    return workspaceDto;
                });
            }
            return Page.empty();
        }catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all Workspaces", ex);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    SYSTEM_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public WorkspaceDto updateWorkspace(WorkspaceDto workspaceDto) {
        try {
            log.info("Updating Workspace");
            Workspace existingWorkspace = workspaceMapper.toEntity(findWorkspaceById(workspaceDto.getId()));
            workspaceMapper.updateFromDto(workspaceDto, existingWorkspace);
            workspaceRepository.save(existingWorkspace);
            workspaceDto.setResponseInfo(buildSuccessInfo());
            return workspaceDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating Workspace");
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    UPDATE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public ErrorResponse deleteWorkspace(Long workspaceId) {
        ErrorResponse errorResponse = new ErrorResponse();
        try {
            log.info("Deleting Workspace with ID {}", workspaceId);
            workspaceRepository.deleteById(findWorkspaceById(workspaceId).getId());
            errorResponse.setResponseInfo(buildSuccessInfo());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting Workspace with ID {}", workspaceId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    DELETE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return errorResponse;
    }
}
