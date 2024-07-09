package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Authority;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.AuthorityRepository;
import com.errabi.sandbox.web.mapper.AuthorityMapper;
import com.errabi.sandbox.web.model.AuthorityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.errabi.sandbox.utils.SandboxConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorityService {
    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;

    @Transactional
    public AuthorityDto createAuthority(AuthorityDto authorityDto){
        try {
            log.info("Creating Authority {} ..", authorityDto.getPermission());
            Authority authority = authorityMapper.toEntity(authorityDto);
            authorityRepository.save(authority);
            return authorityDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the Authority", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    "Unexpected error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public AuthorityDto findAuthorityById(Long authorityId) {
        log.info("Finding Authority with id {}",authorityId);
        Optional<Authority> optionalAuthority =  authorityRepository.findById(authorityId);
        if(optionalAuthority.isPresent()){
            return authorityMapper.toDto(optionalAuthority.get());
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No Authority found",
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<AuthorityDto> findAllAuthorities() {
        try {
            log.info("Fetching all authorities...");
            List<Authority> authorities = authorityRepository.findAll();
            return authorities.stream().map(authorityMapper::toDto).toList();
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all authorities", ex);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    "Unexpected error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public AuthorityDto updateAuthority(AuthorityDto authorityDto) {
        try {
            log.info("Updating authority {} ..", authorityDto.getId());
            Authority existingAuthority = authorityMapper.toEntity(findAuthorityById(authorityDto.getId()));
            authorityMapper.updateFromDto(authorityDto, existingAuthority);
            Authority updatedAuthority = authorityRepository.save(existingAuthority);
            return authorityMapper.toDto(updatedAuthority);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating authority with ID {}", authorityDto.getId());
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    "Unexpected error occurred while updating the authority",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public void deleteAuthority(Long authorityId) {
        try {
            log.info("Deleting authority with ID {}", authorityId);
            authorityRepository.deleteById(findAuthorityById(authorityId).getId());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting authority with ID {}", authorityId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    "Unexpected error occurred while deleting the authority",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
