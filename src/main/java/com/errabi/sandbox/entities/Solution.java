package com.errabi.sandbox.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Solution extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String image;
    @ManyToOne(cascade = CascadeType.ALL)
    private Release release;
}
