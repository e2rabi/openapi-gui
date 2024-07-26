package com.errabi.sandbox.web.controller;

import com.errabi.sandbox.exception.ErrorResponse;
import com.errabi.sandbox.services.ReleaseService;
import com.errabi.sandbox.web.model.ReleaseDto;
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
    public ResponseEntity<List<ReleaseDto>> getAllRelease() {
        List<ReleaseDto> productDto = releaseService.findAllReleases();
        return new ResponseEntity<>(productDto, HttpStatus.OK);
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
