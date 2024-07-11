package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Solution;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.ReleaseRepository;
import com.errabi.sandbox.repositories.SolutionRepository;
import com.errabi.sandbox.web.mapper.SolutionMapper;
import com.errabi.sandbox.web.model.SolutionDto;
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
public class SolutionService {
    private final SolutionRepository solutionRepository;
    private final SolutionMapper solutionMapper;
    private final ReleaseRepository releaseRepository;

    @Transactional
    public SolutionDto createSolution(SolutionDto solutionDto){
        log.info("Creating solution {} ..", solutionDto.getName());
        try {
            Solution solution = solutionMapper.toEntity(solutionDto);
            if(solutionDto.getReleaseId() != null){
                solution.setRelease(releaseRepository.findById(solutionDto.getReleaseId()).orElse(null));
            }
            solutionRepository.save(solution);
            return solutionDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the solution", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    "Unexpected error occurred while saving the solution",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

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
        try {
            log.info("Fetching all solutions...");
            List<Solution> solutions = solutionRepository.findAll();
            return solutions.stream().map(solutionMapper::toDto).toList();
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all solutions", ex);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    "Unexpected error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public SolutionDto updateSolution(SolutionDto solutionDto) {
        try {
            log.info("Updating solution {} ..", solutionDto.getId());
            Solution existingSolution =solutionMapper.toEntity(findSolutionById(solutionDto.getId()));
            solutionMapper.updateFromDto(solutionDto, existingSolution);
            Solution updatedSolution = solutionRepository.save(existingSolution);
            return solutionMapper.toDto(updatedSolution);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating solution with ID {}", solutionDto.getId());
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    "Unexpected error occurred while updating solution",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public void deleteSolution(Long solutionId) {
        try {
            log.info("Deleting solution with ID {}", solutionId);
            solutionRepository.deleteById(findSolutionById(solutionId).getId());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting the solution with ID {}", solutionId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    "Unexpected error occurred while deleting the solution",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
