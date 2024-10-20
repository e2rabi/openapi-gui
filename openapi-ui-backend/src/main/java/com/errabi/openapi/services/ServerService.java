package com.errabi.openapi.services;

import com.errabi.openapi.repositories.ServerRepository;
import com.errabi.openapi.web.mapper.ServerMapper;
import com.errabi.openapi.web.model.ServerModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServerService {
    private final ServerRepository serverRepository;
    private final ServerMapper mapper;

    public void deleteById(Long id) {
        log.debug("Deleting Server with id: {}", id);
        serverRepository.deleteById(id);
    }
    public ServerModel save(ServerModel serverModel) {
        log.debug("Saving Server: {}", serverModel);
        return mapper.toModel(serverRepository.save(mapper.toEntity(serverModel)));
    }
}
