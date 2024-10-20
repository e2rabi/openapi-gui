package com.errabi.openapi.entities.openapi;

import com.errabi.openapi.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Server extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String description;
    @OneToMany(mappedBy = "server",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<ServerVariable> variables;

}
