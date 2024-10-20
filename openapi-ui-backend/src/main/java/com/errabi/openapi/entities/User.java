package com.errabi.openapi.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@EqualsAndHashCode(exclude = "roles", callSuper = false)
@Table(name = "users")
public non-sealed class User extends SecurityBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String username;
    private  String firstName ;
    private  String lastName ;
    private  String email ;
    private  Boolean enabled ;
    private  String password;
    private  String phone ;
    private  String address ;
    private  Boolean accountNonExpired;
    private  Boolean accountNonLocked;
    private  Boolean credentialsNonExpired;
    private  LocalDate expiryDate ;
    private  Boolean firstLoginChangePassword;

    @Builder.Default
    @ManyToMany(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "USER_ID",referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")}
    )
    private Set<Role> roles = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;
}
