package org.adrewdev.apachecamel.exercise3.validations;

import org.adrewdev.apachecamel.exercise3.dto.ResponseData;
import org.apache.camel.Exchange;
import org.apache.camel.ValidationException;

public class Validate {
    private Validate() {
    }

    public static void validateInvoice(Exchange e, ResponseData data) throws ValidationException {

        if (data.getInvoices().isEmpty()) {
            throw new ValidationException(e, "factura vacia");
        }
    }

}
