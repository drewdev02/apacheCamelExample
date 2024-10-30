package org.adrewdev.apachecamel.exercise3.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceSummaryDTO {
    private String invoiceId;            // Cambiado de invoiceNumber
    private String issueDate;            // Cambiado de date
    private CustomerSummaryDTO customer; // Información del cliente agrupada
    private Map<String, ItemSummaryDTO> items; // Items representados como un mapa
    private double totalAmount;           // Total final a pagar
    private PaymentDetailsDTO paymentDetails; // Detalles de pago
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class CustomerSummaryDTO {
    private String fullName;   // Cambiado de name
    private String emailAddress; // Cambiado de email
    private String contactNumber; // Cambiado de phone
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ItemSummaryDTO {
    private int quantity;
    private double price; // Precio unitario
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class PaymentDetailsDTO {
    private String method; // Cambiado de paymentMethod
    private String notes; // Cambiado de notes
}
