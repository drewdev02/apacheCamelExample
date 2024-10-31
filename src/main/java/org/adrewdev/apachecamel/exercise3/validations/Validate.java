package org.adrewdev.apachecamel.exercise3.validations;

import org.adrewdev.apachecamel.exercise3.dto.InvoiceDTO;
import org.apache.camel.Exchange;
import org.apache.camel.ValidationException;

public class Validate {
    private Validate() {
    }

    public static void validateInvoice(Exchange e, InvoiceDTO invoice) throws ValidationException {
        if (invoice.getInvoiceNumber() == null || invoice.getInvoiceNumber().isEmpty()) {
            throw new ValidationException(e, "El número de factura es requerido.");
        }
        if (invoice.getCustomer() == null) {
            throw new ValidationException(e, "La información del cliente es requerida.");
        }
        if (invoice.getItems() == null || invoice.getItems().isEmpty()) {
            throw new ValidationException(e, "Se requiere al menos un artículo en la factura.");
        }
        if (invoice.getSubtotal() <= 0) {
            throw new ValidationException(e, "El subtotal debe ser mayor a cero.");
        }
    }

}
