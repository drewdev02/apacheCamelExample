package org.adrewdev.apachecamel.exercise0.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    int id;
    String name;
    String username;
    String email;
    Address address;
    String phone;
    String website;
    Company company;

}
