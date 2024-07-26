package com.errabi.sandbox.web.controller;

import com.errabi.sandbox.exception.ErrorResponse;
import com.errabi.sandbox.services.ModuleService;
import com.errabi.sandbox.web.model.ModuleDto;
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
public class ModuleController {
    private final ModuleService moduleService;

    @PostMapping("/modules")
    public ResponseEntity<ModuleDto> createModule(@RequestBody @Valid ModuleDto moduleDto){
        return new ResponseEntity<>(moduleService.createModule(moduleDto), HttpStatus.CREATED);
    }

    @GetMapping("/modules/{moduleId}")
    @Cacheable(value = "sandbox", key = "#moduleId")
    public ResponseEntity<ModuleDto> getModuleById(@PathVariable  Long moduleId){
        return new ResponseEntity<>(moduleService.findModuleById(moduleId), HttpStatus.OK);
    }

    @GetMapping("/solutions/{id}/modules")
    public ResponseEntity<List<ModuleDto>> getModuleBySolutionId(@PathVariable Long id){
        List<ModuleDto> modulesDto = moduleService.getModuleBySolutionId(id);
        return new ResponseEntity<>(modulesDto, HttpStatus.OK);
    }

    @GetMapping("/modules")
    public ResponseEntity<List<ModuleDto>> getAllModules() {
        List<ModuleDto> modulesDto = moduleService.findAllModules();
        return new ResponseEntity<>(modulesDto, HttpStatus.OK);
    }

    @PutMapping("/modules")
    public ResponseEntity<ModuleDto> updateModule(@RequestBody @Valid ModuleDto moduleDto) {
        return new ResponseEntity<>(moduleService.updateModule(moduleDto), HttpStatus.OK);
    }

    @DeleteMapping("/modules/{moduleId}")
    public ResponseEntity<ErrorResponse> deleteSolution(@PathVariable Long moduleId){
        return new ResponseEntity<>(moduleService.deleteModule(moduleId), HttpStatus.OK);
    }
}

