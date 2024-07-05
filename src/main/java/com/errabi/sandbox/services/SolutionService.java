package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Solution;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.SolutionRepository;
import com.errabi.sandbox.web.mapper.SolutionMapper;
import com.errabi.sandbox.web.model.SolutionDto;
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
public class SolutionService {
    private final SolutionRepository solutionRepository;
    private final SolutionMapper solutionMapper;
    private final ReleaseService releaseService;

    @Transactional
    public SolutionDto createSolution(SolutionDto solutionDto){
        log.info("Creating solution {} ..", solutionDto.getName());
        try {
            Solution solution = solutionMapper.toEntity(solutionDto);
            if(solutionDto.getReleaseId() != null){
                solution.setRelease(releaseService.getReleaseById(solutionDto.getReleaseId()));
            }
            solutionRepository.save(solution);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the solution", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    "Unexpected error occurred while saving the solution",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return solutionDto;
    }

    @Transactional
    public SolutionDto findSolutionById(Long solutionId) {
        log.info("Finding solution with id {}",solutionId);
        Optional<Solution> optionalSolution =  solutionRepository.findById(solutionId);
        if(optionalSolution.isPresent()){
            return solutionMapper.toDto(optionalSolution.get());
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No solution found",
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<SolutionDto> getSolutionByReleaseId(Long releaseId){
        log.info("Finding solutions with release id");
        List<SolutionDto> solutionsDto = solutionRepository.findSolutionsByReleaseId(releaseId).stream()
                .map(solutionMapper::toDto)
                .toList();
        if(!solutionsDto.isEmpty()){
            return solutionsDto;
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No solutions found",
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<SolutionDto> findAllSolutions() {
        log.info("Fetching all solutions...");
        List<Solution> solutions;
        try {
            solutions = solutionRepository.findAll();
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all solutions", ex);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    "Unexpected error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        if (solutions.isEmpty()) {
            log.warn("No release found in the database.");
            return Collections.emptyList();
        }
        return solutions.stream().map(solutionMapper::toDto).toList();
    }

    public SolutionDto updateSolution(SolutionDto solutionDto) {
        log.info("Updating solution {} ..", solutionDto.getId());
        Solution existingSolution = solutionRepository.findById(solutionDto.getId()).orElse(null);
        if (existingSolution == null) {
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No Solution was found",
                    HttpStatus.NOT_FOUND
            );
        }
        try {
            solutionMapper.updateFromDto(solutionDto, existingSolution);
            Solution updatedSolution = solutionRepository.save(existingSolution);
            return solutionMapper.toDto(updatedSolution);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating solution with ID {}", solutionDto.getId());
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    "Unexpected error occurred while updating product",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public void deleteSolution(Long solutionId) {
        log.info("Deleting solution with ID {}", solutionId);
        if (!solutionRepository.existsById(solutionId)) {
            log.error("Solution not found");
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "release not found",
                    HttpStatus.NOT_FOUND
            );
        }
        try {
            solutionRepository.deleteById(solutionId);
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting the release with ID {}", solutionId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    "Unexpected error occurred while deleting the release",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
