package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Module;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.ModuleRepository;
import com.errabi.sandbox.repositories.SolutionRepository;
import com.errabi.sandbox.web.mapper.ModuleMapper;
import com.errabi.sandbox.web.model.ModuleDto;
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
public class ModuleService {
    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;
    private final SolutionRepository solutionRepository;

    @Transactional
    public ModuleDto createModule(ModuleDto moduleDto){
        try {
            log.info("Creating module {} ..", moduleDto.getName());
            Module module = moduleMapper.toEntity(moduleDto);
            if(moduleDto.getSolutionId() != null){
                module.setSolution(solutionRepository.findById(moduleDto.getSolutionId()).orElse(null));
            }
            moduleRepository.save(module);
            return moduleDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the module", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    "Unexpected error occurred while saving the module",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

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
        try {
            log.info("Fetching all modules...");
            List<Module> modules = moduleRepository.findAll();
            return modules.stream().map(moduleMapper::toDto).toList();
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all modules", ex);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    "Unexpected error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public ModuleDto updateModule(ModuleDto moduleDto) {
        try {
            log.info("Updating module {} ..", moduleDto.getId());
            Module existingModule = moduleMapper.toEntity(findModuleById(moduleDto.getId()));
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

    @Transactional
    public void deleteModule(Long moduleId) {
        try {
            log.info("Deleting module with ID {}", moduleId);
            moduleRepository.deleteById(findModuleById(moduleId).getId());
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
