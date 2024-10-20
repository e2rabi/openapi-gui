package com.errabi.openapi.web.controller;

import com.errabi.openapi.exception.ErrorResponse;
import com.errabi.openapi.services.ReleaseService;
import com.errabi.openapi.web.model.ReleaseDto;
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
public class ReleaseController {
    private final ReleaseService releaseService;

    @PostMapping("/releases")
    public ResponseEntity<ReleaseDto> createRelease(@RequestBody @Valid ReleaseDto releaseDto){
        return new ResponseEntity<>(releaseService.createRelease(releaseDto), HttpStatus.CREATED);
    }

    @GetMapping("/releases/{releaseId}")
    @Cacheable(value = "sandbox", key = "#releaseId")
    public ResponseEntity<ReleaseDto> getReleaseById(@PathVariable  Long releaseId){
        return new ResponseEntity<>(releaseService.findReleaseById(releaseId), HttpStatus.OK);
    }

    @GetMapping("/releases")
    public ResponseEntity<Page<ReleaseDto>> getAllRelease(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(releaseService.findAllReleases(PageRequest.of(page,pageSize)), HttpStatus.OK);
    }

    @GetMapping("/products/{id}/releases")
    public ResponseEntity<List<ReleaseDto>> getReleaseByProductId(@PathVariable  Long id){
        List<ReleaseDto> releasesDto = releaseService.getReleaseByProductId(id);
        return new ResponseEntity<>(releasesDto, HttpStatus.OK);
    }

    @PutMapping("/releases")
    public ResponseEntity<ReleaseDto> updateRelease(@RequestBody @Valid ReleaseDto releaseDto) {
        return new ResponseEntity<>(releaseService.updateRelease(releaseDto), HttpStatus.OK);
    }

    @DeleteMapping("/releases/{releaseId}")
    public ResponseEntity<ErrorResponse> deleteRelease(@PathVariable Long releaseId){
        return new ResponseEntity<>(releaseService.deleteRelease(releaseId), HttpStatus.OK);
    }
}
