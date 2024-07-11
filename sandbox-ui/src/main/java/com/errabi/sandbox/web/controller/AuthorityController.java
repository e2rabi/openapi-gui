package com.errabi.sandbox.web.controller;

import com.errabi.sandbox.services.AuthorityService;
import com.errabi.sandbox.web.model.AuthorityDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/authorities/{id}")
    public ResponseEntity<AuthorityDto> getAuthorityById(@PathVariable  Long id){
        return new ResponseEntity<>(authorityService.findAuthorityById(id), HttpStatus.OK);
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
    public ResponseEntity<Void> deleteAuthority(@PathVariable Long authorityId){
        authorityService.deleteAuthority(authorityId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}