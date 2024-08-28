package com.errabi.sandbox.entities;

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
@EqualsAndHashCode(exclude = {"users", "authorities"})
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_authority",
            joinColumns = {@JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID",referencedColumnName = "ID")}
    )
    private Set<Authority> authorities = new HashSet<>();
}
