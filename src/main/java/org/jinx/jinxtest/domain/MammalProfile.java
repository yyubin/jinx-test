package org.jinx.jinxtest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MammalProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diet;

    @OneToOne
    @JoinColumn(name = "mammal_id", nullable = false)
    private Mammal mammal;
}