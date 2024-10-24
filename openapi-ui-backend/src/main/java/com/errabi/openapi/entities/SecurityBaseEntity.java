package com.errabi.openapi.entities;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract sealed class SecurityBaseEntity permits User {
    @CreatedDate
    protected LocalDateTime created ;
    @LastModifiedDate
    protected LocalDateTime lastModified ;
    @CreatedBy
    protected String createdBy ;
    @LastModifiedBy
    protected String lastModifiedBy ;
    @Version
    protected int version ;

}
