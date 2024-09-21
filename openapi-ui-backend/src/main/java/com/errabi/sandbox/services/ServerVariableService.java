package com.errabi.sandbox.services;

import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.ServerVariableRepository;
import com.errabi.sandbox.web.mapper.ServerVariableMapper;
import com.errabi.sandbox.web.model.ServerVariableModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.errabi.sandbox.utils.SandboxConstant.NOT_FOUND_ERROR_CODE;
import static com.errabi.sandbox.utils.SandboxConstant.NOT_FOUND_ERROR_DESCRIPTION;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServerVariableService {
    private final ServerVariableRepository serverVariableRepository;
    private final ServerVariableMapper mapper ;

    @Transactional
    public void deleteById(Long id) {
        log.debug("Deleting ServerVariable with id: {}", id);
        serverVariableRepository.deleteById(id);
    }
   @Transactional
   public ServerVariableModel save(ServerVariableModel serverVariableModel) {
        log.debug("Saving ServerVariable: {}", serverVariableModel);
        return mapper.toModel(serverVariableRepository.save(mapper.toEntity(serverVariableModel)));
   }
   @Transactional(readOnly = true)
   public ServerVariableModel findById(Long id) {
       log.debug("Finding ServerVariable with id: {}", id);
       return mapper.toModel(serverVariableRepository.findById(id).orElseThrow(() -> new TechnicalException(
                NOT_FOUND_ERROR_CODE,
                NOT_FOUND_ERROR_DESCRIPTION,
                HttpStatus.NOT_FOUND
        )));
   }
   public List<ServerVariableModel> findAll() {
       log.debug("Finding all ServerVariable");
       return serverVariableRepository.findAll().stream()
               .map(mapper::toModel)
               .toList();
   }
}
