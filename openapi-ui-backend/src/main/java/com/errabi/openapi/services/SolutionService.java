package com.errabi.openapi.services;

import com.errabi.openapi.entities.Solution;
import com.errabi.openapi.exception.ErrorResponse;
import com.errabi.openapi.exception.TechnicalException;
import com.errabi.openapi.repositories.ReleaseRepository;
import com.errabi.openapi.repositories.SolutionRepository;
import com.errabi.openapi.web.mapper.SolutionMapper;
import com.errabi.openapi.web.model.SolutionDto;
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
import static com.errabi.openapi.utils.SandboxConstant.*;
import static com.errabi.openapi.utils.SandboxUtils.buildSuccessInfo;

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
            solution = solutionRepository.save(solution);
            solutionDto = solutionMapper.toDto(solution);
            solutionDto.setResponseInfo(buildSuccessInfo());
            return solutionDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the solution");
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    SAVE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public SolutionDto findSolutionById(Long solutionId) {
        log.info("Finding solution with id {}",solutionId);
        Optional<Solution> optionalSolution =  solutionRepository.findById(solutionId);
        if(optionalSolution.isPresent()){
            SolutionDto solutionDto = solutionMapper.toDto(optionalSolution.get());
            solutionDto.setResponseInfo(buildSuccessInfo());
            return solutionDto;
        }else{
            log.info("Could not find solution with id {}",solutionId);
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    NOT_FOUND_ERROR_DESCRIPTION,
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<SolutionDto> getSolutionByReleaseId(Long releaseId){
        try {
            log.info("Finding solutions with release id");
            List<Solution> solutions = solutionRepository.findSolutionsByReleaseId(releaseId);
            if (!solutions.isEmpty()) {
                return solutions.stream()
                        .map(solutionMapper::toDto)
                        .peek(solutionDto -> solutionDto.setResponseInfo(buildSuccessInfo()))
                        .toList();
            } else {
                return Collections.emptyList();
            }
        }catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all solutions with release id");
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    SYSTEM_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public Page<SolutionDto> findAllSolutions(Pageable pageable) {
        try {
            log.info("Fetching all solutions...");
            Page<Solution> solutions = solutionRepository.findAllWithReleaseNames(pageable);
            if (!solutions.isEmpty()) {
                return solutions.map(solution -> {
                            SolutionDto solutionDto = solutionMapper.toDto(solution);
                            solutionDto.setReleaseName(solution.getRelease().getName());
                            return solutionDto;
                        });
            } else {
                return Page.empty();
            }
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all solutions");
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    SYSTEM_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public SolutionDto updateSolution(SolutionDto solutionDto) {
        try {
            log.info("Updating solution");
            Solution existingSolution =solutionMapper.toEntity(findSolutionById(solutionDto.getId()));
            solutionMapper.updateFromDto(solutionDto, existingSolution);
            solutionRepository.save(existingSolution);
            solutionDto.setResponseInfo(buildSuccessInfo());
            return solutionDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating solution");
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    UPDATE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public ErrorResponse deleteSolution(Long solutionId) {
        ErrorResponse errorResponse = new ErrorResponse();
        try {
            log.info("Deleting solution with ID {}", solutionId);
            solutionRepository.deleteById(findSolutionById(solutionId).getId());
            errorResponse.setResponseInfo(buildSuccessInfo());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting the solution with ID {}", solutionId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    DELETE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return errorResponse;
    }
}
