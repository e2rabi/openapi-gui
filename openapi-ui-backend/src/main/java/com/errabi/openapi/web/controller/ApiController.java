package com.errabi.openapi.web.controller;

import com.errabi.openapi.exception.ErrorResponse;
import com.errabi.openapi.services.ApiService;
import com.errabi.openapi.web.model.ApiDto;
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
public class ApiController {
    private final ApiService apiService;

    @PostMapping("/api")
    public ResponseEntity<ApiDto> createApi(@RequestBody @Valid ApiDto apiDto){
        return new ResponseEntity<>(apiService.createApi(apiDto), HttpStatus.CREATED);
    }

    @GetMapping("/api/{apiId}")
    @Cacheable(value = "sandbox", key = "#apiId")
    public ResponseEntity<ApiDto> getApiById(@PathVariable  Long apiId){
        return new ResponseEntity<>(apiService.findApiById(apiId), HttpStatus.OK);
    }

    @GetMapping("/modules/{id}/api")
    public ResponseEntity<List<ApiDto>> getApiByModuleId(@PathVariable Long id){
        List<ApiDto> apisDto = apiService.getApiByModuleId(id);
        return new ResponseEntity<>(apisDto, HttpStatus.OK);
    }

    @GetMapping("/api")
    public ResponseEntity<Page<ApiDto>> getAllApi(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(apiService.findAllApi(PageRequest.of(page,pageSize)), HttpStatus.OK);
    }

    @PutMapping("/api")
    public ResponseEntity<ApiDto> updateApi(@RequestBody @Valid ApiDto apiDto) {
        return new ResponseEntity<>(apiService.updateApi(apiDto), HttpStatus.OK);
    }

    @DeleteMapping("/api/{apiId}")
    public ResponseEntity<ErrorResponse> deleteApi(@PathVariable Long apiId){
        return new ResponseEntity<>(apiService.deleteApi(apiId), HttpStatus.OK);
    }
}
