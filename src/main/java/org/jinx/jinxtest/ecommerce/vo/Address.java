package org.jinx.jinxtest.ecommerce.vo;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    public String toFullAddress() {
        return String.join(", ", street, city, state, postalCode, country);
    }
}
