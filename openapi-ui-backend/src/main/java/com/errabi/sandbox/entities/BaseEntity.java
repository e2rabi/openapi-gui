package com.errabi.sandbox.entities;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseEntity {
    protected Boolean enabled ;
    protected Boolean visibility ;
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
