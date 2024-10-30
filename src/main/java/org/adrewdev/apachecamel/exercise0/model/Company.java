package org.adrewdev.apachecamel.exercise0.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    String name;
    String catchPhrase;
    String bs;
}