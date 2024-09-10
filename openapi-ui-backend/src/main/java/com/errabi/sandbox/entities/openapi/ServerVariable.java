package com.errabi.sandbox.entities.openapi;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerVariable {
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
