package org.adrewdev.apachecamel.exercise0.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    String street;
    String suite;
    String city;
    String zipcode;
    Geo geo;
}