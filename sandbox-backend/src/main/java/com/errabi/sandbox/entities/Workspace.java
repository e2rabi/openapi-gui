package com.errabi.sandbox.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@DynamicUpdate
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Workspace extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String image ;
    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;
    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> users;
}
