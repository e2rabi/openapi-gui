package com.errabi.sandbox.entities;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {
    protected String enabled ;
    protected boolean visibility ;
    protected String color ;
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
