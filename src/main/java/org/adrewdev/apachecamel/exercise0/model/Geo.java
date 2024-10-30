package org.adrewdev.apachecamel.exercise0.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geo {
    String lat;
    String lng;
}