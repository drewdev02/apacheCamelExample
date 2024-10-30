package org.adrewdev.apachecamel.exercise1.errors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ErrorHandlerProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        var exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

        var errorResponse = new HashMap<>();
        errorResponse.put("error", exception != null ? exception.getMessage() : "Unknown error");
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());

        exchange.getIn().setBody(errorResponse);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.BAD_REQUEST.value());
    }
}
