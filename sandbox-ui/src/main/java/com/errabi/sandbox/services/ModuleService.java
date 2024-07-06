package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Module;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.ModuleRepository;
import com.errabi.sandbox.web.mapper.ModuleMapper;
import com.errabi.sandbox.web.model.ModuleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static com.errabi.sandbox.utils.SandboxConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModuleService {
    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;
    private final SolutionService solutionService;

    @Transactional
    public ModuleDto createModule(ModuleDto moduleDto){
        log.info("Creating module {} ..", moduleDto.getName());
        try {
            Module module = moduleMapper.toEntity(moduleDto);
            if(moduleDto.getSolutionId() != null){
                module.setSolution(solutionService.getSolutionById(moduleDto.getSolutionId()));
            }
            moduleRepository.save(module);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the module", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    "Unexpected error occurred while saving the module",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return moduleDto;
    }

    @Transactional
    public ModuleDto findModuleById(Long moduleId) {
        log.info("Finding module with id {}",moduleId);
        Optional<Module> optionalModule = moduleRepository.findById(moduleId);
        if(optionalModule.isPresent()){
            return moduleMapper.toDto(optionalModule.get());
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No module found",
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<ModuleDto> getModuleBySolutionId(Long solutionId){
        log.info("Finding Modules with solution id");
        List<ModuleDto> modulesDto = moduleRepository.findModulesBySolutionId(solutionId).stream()
                .map(moduleMapper::toDto)
                .toList();
        if(!modulesDto.isEmpty()){
            return modulesDto;
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No modules found",
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<ModuleDto> findAllModules() {
        log.info("Fetching all modules...");
        List<Module> modules;
        try {
            modules = moduleRepository.findAll();
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all modules", ex);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    "Unexpected error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        if (modules.isEmpty()) {
            log.warn("No modules found in the database.");
            return Collections.emptyList();
        }
        return modules.stream().map(moduleMapper::toDto).toList();
    }

    public ModuleDto updateModule(ModuleDto moduleDto) {
        log.info("Updating module {} ..", moduleDto.getId());
        Module existingModule = moduleRepository.findById(moduleDto.getId()).orElse(null);
        if (existingModule == null) {
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No module was found",
                    HttpStatus.NOT_FOUND
            );
        }
        try {
            moduleMapper.updateFromDto(moduleDto, existingModule);
            Module updatedModule = moduleRepository.save(existingModule);
            return moduleMapper.toDto(updatedModule);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating module with ID {}", moduleDto.getId());
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    "Unexpected error occurred while updating module",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public void deleteModule(Long moduleId) {
        log.info("Deleting module with ID {}", moduleId);
        if (!moduleRepository.existsById(moduleId)) {
            log.error("Module not found");
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No Module found",
                    HttpStatus.NOT_FOUND
            );
        }
        try {
            moduleRepository.deleteById(moduleId);
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting the module with ID {}", moduleId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    "Unexpected error occurred while deleting the module",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
