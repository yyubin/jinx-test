package org.jinx.jinxtest.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Mammal extends Animal {
    private boolean hasFur;

    @OneToOne(mappedBy = "mammal", cascade = CascadeType.ALL, orphanRemoval = true)
    private MammalProfile profile;
}