package com.errabi.openapi.web.controller;

import com.errabi.openapi.exception.ErrorResponse;
import com.errabi.openapi.services.ConfigurationService;
import com.errabi.openapi.web.model.ConfigurationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sandbox-api/v1/")
@RequiredArgsConstructor
public class ConfigurationController {
    private final ConfigurationService configurationService;

    @PostMapping("/configurations")
    public ResponseEntity<ConfigurationDto> createConfig(@RequestBody @Valid ConfigurationDto configurationDto){
        return new ResponseEntity<>(configurationService.createConfig(configurationDto), HttpStatus.CREATED);
    }

    @GetMapping("/configurations/{configId}")
    @Cacheable(value = "sandbox", key = "#configId")
    public ResponseEntity<ConfigurationDto> getConfigById(@PathVariable Long configId){
        return new ResponseEntity<>(configurationService.findConfigById(configId), HttpStatus.OK);
    }

    @GetMapping("/configurations/keys/{key}")
    public ResponseEntity<ConfigurationDto> getConfigByKey(@PathVariable String key){
        return new ResponseEntity<>(configurationService.findConfigByKey(key), HttpStatus.OK);
    }

    @GetMapping("/configurations")
    public ResponseEntity<Page<ConfigurationDto>> getAllConfig(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ConfigurationDto> configurations = configurationService.findAllConfigurations(page, size);
        return new ResponseEntity<>(configurations, HttpStatus.OK);
    }

    @DeleteMapping("/configurations/{configId}")
    public ResponseEntity<ErrorResponse> deleteConfig(@PathVariable Long configId){
        return new ResponseEntity<>(configurationService.deleteConfiguration(configId), HttpStatus.OK);
    }
}
