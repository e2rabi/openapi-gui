package com.errabi.sandbox.web.controller;

import com.errabi.sandbox.services.ApiService;
import com.errabi.sandbox.web.model.ApiDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sandbox-api/v1/")
@RequiredArgsConstructor
public class ApiController {
    private final ApiService apiService;

    @PostMapping("/api")
    public ResponseEntity<ApiDto> createApi(@RequestBody @Valid ApiDto apiDto){
        return new ResponseEntity<>(apiService.createApi(apiDto), HttpStatus.CREATED);
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<ApiDto> getApiById(@PathVariable  Long id){
        return new ResponseEntity<>(apiService.findApiById(id), HttpStatus.OK);
    }

    @GetMapping("/modules/{id}/api")
    public ResponseEntity<List<ApiDto>> getApiByModuleId(@PathVariable Long id){
        List<ApiDto> apisDto = apiService.getApiByModuleId(id);
        return new ResponseEntity<>(apisDto, HttpStatus.OK);
    }

    @GetMapping("/api")
    public ResponseEntity<List<ApiDto>> getAllApi() {
        List<ApiDto> apisDto = apiService.findAllApi();
        return new ResponseEntity<>(apisDto, HttpStatus.OK);
    }

    @PutMapping("/api")
    public ResponseEntity<ApiDto> updateApi(@RequestBody @Valid ApiDto apiDto) {
        return new ResponseEntity<>(apiService.updateApi(apiDto), HttpStatus.OK);
    }

    @DeleteMapping("/api/{apiId}")
    public ResponseEntity<Void> deleteApi(@PathVariable Long apiId){
        apiService.deleteApi(apiId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}