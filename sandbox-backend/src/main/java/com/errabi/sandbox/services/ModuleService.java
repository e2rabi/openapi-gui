package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Module;
import com.errabi.sandbox.exception.ErrorResponse;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.ModuleRepository;
import com.errabi.sandbox.repositories.SolutionRepository;
import com.errabi.sandbox.web.mapper.ModuleMapper;
import com.errabi.sandbox.web.model.ModuleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static com.errabi.sandbox.utils.SandboxConstant.*;
import static com.errabi.sandbox.utils.SandboxUtils.buildSuccessInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModuleService {
    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;
    private final SolutionRepository solutionRepository;
    private final SolutionService solutionService;

    @Transactional
    public ModuleDto createModule(ModuleDto moduleDto){
        try {
            log.info("Creating module {} ..", moduleDto.getName());
            Module module = moduleMapper.toEntity(moduleDto);
            if(moduleDto.getSolutionId() != null){
                module.setSolution(solutionRepository.findById(moduleDto.getSolutionId()).orElse(null));
            }
            module = moduleRepository.save(module);
            moduleDto = moduleMapper.toDto(module);
            moduleDto.setResponseInfo(buildSuccessInfo());
            return moduleDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the module");
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    SAVE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public ModuleDto findModuleById(Long moduleId) {
        log.info("Finding module with id {}",moduleId);
        Optional<Module> optionalModule = moduleRepository.findById(moduleId);
        if(optionalModule.isPresent()){
            ModuleDto moduleDto = moduleMapper.toDto(optionalModule.get());
            moduleDto.setResponseInfo(buildSuccessInfo());
            return moduleDto;
        }else{
            log.info("Could not find module with id {}",moduleId);
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    NOT_FOUND_ERROR_DESCRIPTION,
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<ModuleDto> getModuleBySolutionId(Long solutionId){
        try {
            log.info("Finding Modules with solution id");
            List<Module> modules = moduleRepository.findModulesBySolutionId(solutionId);
            if (!modules.isEmpty()) {
                return modules.stream()
                        .map(moduleMapper::toDto)
                        .peek(moduleDto -> moduleDto.setResponseInfo(buildSuccessInfo()))
                        .toList();
            } else {
                return Collections.emptyList();
            }
        }catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all modules with solution id");
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    SYSTEM_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Page<ModuleDto> findAllModules(Pageable pageable) {
        try {
            log.info("Fetching all modules...");
            Page<Module> modules = moduleRepository.findAll(pageable);
            if (!modules.isEmpty()) {
                return modules.map(module -> {
                    ModuleDto moduleDto = moduleMapper.toDto(module);
                    moduleDto.setSolutionName(solutionService.findSolutionById(module.getSolution().getId()).getName());
                    return moduleDto;
                });

            } else {
                return Page.empty();
            }
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all modules");
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    SYSTEM_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public ModuleDto updateModule(ModuleDto moduleDto) {
        try {
            log.info("Updating module");
            Module existingModule = moduleMapper.toEntity(findModuleById(moduleDto.getId()));
            moduleMapper.updateFromDto(moduleDto, existingModule);
            moduleRepository.save(existingModule);
            moduleDto.setResponseInfo(buildSuccessInfo());
            return moduleDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating module");
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    UPDATE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public ErrorResponse deleteModule(Long moduleId) {
        ErrorResponse errorResponse = new ErrorResponse();
        try {
            log.info("Deleting module with ID {}", moduleId);
            moduleRepository.deleteById(findModuleById(moduleId).getId());
            errorResponse.setResponseInfo(buildSuccessInfo());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting the module with ID {}", moduleId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    DELETE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return errorResponse;
    }
}
