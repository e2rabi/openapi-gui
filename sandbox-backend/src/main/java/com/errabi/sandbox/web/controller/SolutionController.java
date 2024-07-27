package com.errabi.sandbox.web.controller;

import com.errabi.sandbox.exception.ErrorResponse;
import com.errabi.sandbox.services.SolutionService;
import com.errabi.sandbox.web.model.SolutionDto;
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
public class SolutionController {
    private final SolutionService solutionService;

    @PostMapping("/solutions")
    public ResponseEntity<SolutionDto> createSolution(@RequestBody @Valid SolutionDto solutionDto){
        return new ResponseEntity<>(solutionService.createSolution(solutionDto), HttpStatus.CREATED);
    }

    @GetMapping("/solutions/{solutionId}")
    @Cacheable(value = "sandbox", key = "#solutionId")
    public ResponseEntity<SolutionDto> getSolutionById(@PathVariable  Long solutionId){
        return new ResponseEntity<>(solutionService.findSolutionById(solutionId), HttpStatus.OK);
    }

    @GetMapping("/releases/{id}/solutions")
    public ResponseEntity<List<SolutionDto>> getSolutionByReleaseId(@PathVariable Long id){
        List<SolutionDto> solutionsDto = solutionService.getSolutionByReleaseId(id);
        return new ResponseEntity<>(solutionsDto, HttpStatus.OK);
    }

    @GetMapping("/solutions")
    public ResponseEntity<List<SolutionDto>> getAllSolutions() {
        List<SolutionDto> solutionDto = solutionService.findAllSolutions();
        return new ResponseEntity<>(solutionDto, HttpStatus.OK);
    }

    @PutMapping("/solutions")
    public ResponseEntity<SolutionDto> updateSolution(@RequestBody @Valid SolutionDto solutionDto) {
        return new ResponseEntity<>(solutionService.updateSolution(solutionDto), HttpStatus.OK);
    }

    @DeleteMapping("/solutions/{solutionId}")
    public ResponseEntity<ErrorResponse> deleteSolution(@PathVariable Long solutionId){
        return new ResponseEntity<>(solutionService.deleteSolution(solutionId),HttpStatus.OK);
    }
}