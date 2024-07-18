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
@EqualsAndHashCode(exclude = "roles")
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String username;
    private  String firstName ;
    private  String lastName ;
    private  String email ;
    private  String password;
    private  String phone ;
    private  String address ;
    private  boolean accountNonExpired;
    private  boolean accountNonLocked;
    private  boolean credentialsNonExpired;
    @ManyToMany(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "USER_ID",referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")}
    )
    private Set<Role> roles = new HashSet<>();
}
