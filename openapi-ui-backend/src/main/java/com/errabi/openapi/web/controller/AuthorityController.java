package com.errabi.openapi.web.controller;

import com.errabi.openapi.exception.ErrorResponse;
import com.errabi.openapi.services.AuthorityService;
import com.errabi.openapi.web.model.AuthorityDto;
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
public class AuthorityController {
    private final AuthorityService authorityService;

    @PostMapping("/authorities")
    public ResponseEntity<AuthorityDto> createAuthority(@RequestBody @Valid AuthorityDto authorityDto){
        return new ResponseEntity<>(authorityService.createAuthority(authorityDto), HttpStatus.CREATED);
    }

    @GetMapping("/authorities/{authId}")
    @Cacheable(value = "sandbox", key = "#authId")
    public ResponseEntity<AuthorityDto> getAuthorityById(@PathVariable  Long authId){
        return new ResponseEntity<>(authorityService.findAuthorityById(authId), HttpStatus.OK);
    }

    @GetMapping("/authorities")
    public ResponseEntity<List<AuthorityDto>> getAllAuthorities() {
        List<AuthorityDto> authoritiesDto = authorityService.findAllAuthorities();
        return new ResponseEntity<>(authoritiesDto, HttpStatus.OK);
    }

    @PutMapping("/authorities")
    public ResponseEntity<AuthorityDto> updateAuthority(@RequestBody @Valid AuthorityDto authorityDto) {
        return new ResponseEntity<>(authorityService.updateAuthority(authorityDto), HttpStatus.OK);
    }

    @DeleteMapping("/authorities/{authorityId}")
    public ResponseEntity<ErrorResponse> deleteAuthority(@PathVariable Long authorityId){
        return new ResponseEntity<>(authorityService.deleteAuthority(authorityId), HttpStatus.OK);
    }
}