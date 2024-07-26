package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Authority;
import com.errabi.sandbox.exception.ErrorResponse;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.AuthorityRepository;
import com.errabi.sandbox.web.mapper.AuthorityMapper;
import com.errabi.sandbox.web.model.AuthorityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.errabi.sandbox.utils.SandboxConstant.*;
import static com.errabi.sandbox.utils.SandboxUtils.buildSuccessInfo;

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
            Authority authority = authorityRepository.save(authorityMapper.toEntity(authorityDto));
            authorityDto = authorityMapper.toDto(authority);
            authorityDto.setResponseInfo(buildSuccessInfo());
            return authorityDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the Authority", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    SAVE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public AuthorityDto findAuthorityById(Long authorityId) {
        log.info("Finding Authority with id {}",authorityId);
        Optional<Authority> optionalAuthority =  authorityRepository.findById(authorityId);
        if(optionalAuthority.isPresent()){
            AuthorityDto authorityDto = authorityMapper.toDto(optionalAuthority.get());
            authorityDto.setResponseInfo(buildSuccessInfo());
            return authorityDto;
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    NOT_FOUND_ERROR_DESCRIPTION,
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<AuthorityDto> findAllAuthorities() {
        try {
            log.info("Fetching all authorities...");
            List<Authority> authorities = authorityRepository.findAll();
            if(!authorities.isEmpty()){
                return authorities.stream()
                        .map(authorityMapper::toDto)
                        .peek(authorityDto -> authorityDto.setResponseInfo(buildSuccessInfo()))
                        .toList();
            }else{
                return Collections.emptyList();
            }
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all authorities", ex);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    SYSTEM_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public AuthorityDto updateAuthority(AuthorityDto authorityDto) {
        try {
            log.info("Updating authority {} ..", authorityDto.getId());
            Authority existingAuthority = authorityMapper.toEntity(findAuthorityById(authorityDto.getId()));
            authorityMapper.updateFromDto(authorityDto, existingAuthority);
            authorityRepository.save(existingAuthority);
            authorityDto.setResponseInfo(buildSuccessInfo());
            return authorityDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating authority with ID {}", authorityDto.getId());
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    UPDATE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public ErrorResponse deleteAuthority(Long authorityId) {
        ErrorResponse errorResponse = new ErrorResponse();
        try {
            log.info("Deleting authority with ID {}", authorityId);
            authorityRepository.deleteById(findAuthorityById(authorityId).getId());
            errorResponse.setResponseInfo(buildSuccessInfo());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting authority with ID {}", authorityId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    DELETE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return errorResponse;
    }
}
