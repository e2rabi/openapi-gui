package com.errabi.openapi.web.controller;

import com.errabi.openapi.services.ServerVariableService;
import com.errabi.openapi.web.model.ServerVariableModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/openapi/v1/")
@RequiredArgsConstructor
public class ServerVariableController {
    private final ServerVariableService serverVariableService;

    @PostMapping("/server-variables")
    public ResponseEntity<ServerVariableModel> createServerVariable(@RequestBody ServerVariableModel serverVariableModel, UriComponentsBuilder ucb) {
        var savedServerVariable = serverVariableService.save(serverVariableModel);
        var uri = ucb.path("/server-variables/{id}")
                .buildAndExpand(savedServerVariable.id())
                .toUri();
        return ResponseEntity.created(uri).body(savedServerVariable);
    }
}
