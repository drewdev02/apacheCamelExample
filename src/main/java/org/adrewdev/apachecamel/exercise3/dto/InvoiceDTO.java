package org.adrewdev.apachecamel.exercise3.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
    private String invoiceNumber;
    private String date;
    private CustomerDTO customer;
    private List<ItemDTO> items;
    private double subtotal;
    private double tax;
    private double total;
    private String paymentMethod;
    private String notes;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class CustomerDTO {
    private String name;
    private String email;
    private String phone;
    private AddressDTO address;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class AddressDTO {
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ItemDTO {
    private String description;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
}
