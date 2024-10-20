package com.errabi.openapi.web.controller;

import com.errabi.openapi.exception.ErrorResponse;
import com.errabi.openapi.services.SolutionService;
import com.errabi.openapi.web.model.SolutionDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<Page<SolutionDto>> getAllSolutions(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(solutionService.findAllSolutions(PageRequest.of(page,pageSize)), HttpStatus.OK);
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
