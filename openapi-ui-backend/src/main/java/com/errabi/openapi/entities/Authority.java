package com.errabi.openapi.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@EqualsAndHashCode(exclude = "roles")
@Table(name = "authorities")
public class Authority{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String permission ;
    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles = new HashSet<>();
}
