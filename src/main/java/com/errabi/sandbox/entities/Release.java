package com.errabi.sandbox.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Release extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;
    @OneToMany(mappedBy = "release", fetch = FetchType.LAZY)
    private List<Solution> solutions;
}
