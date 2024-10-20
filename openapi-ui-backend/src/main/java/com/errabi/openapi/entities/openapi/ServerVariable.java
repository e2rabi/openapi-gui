package com.errabi.openapi.entities.openapi;

import com.errabi.openapi.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ServerVariable extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String defaultValue;
    private String description;
    private String enumValues;
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    private Server server;

}
