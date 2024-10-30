package org.adrewdev.apachecamel.exercise3.camelController;

import lombok.RequiredArgsConstructor;
import org.adrewdev.apachecamel.exercise3.dto.InvoiceDTO;
import org.adrewdev.apachecamel.exercise3.dto.InvoiceMapper;
import org.adrewdev.apachecamel.exercise3.dto.InvoiceSummaryDTO;
import org.adrewdev.apachecamel.exercise3.errors.ErrorHandlerProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Exercise3 extends RouteBuilder {

    private final ErrorHandlerProcessor errorHandlerProcessor;
    private final InvoiceMapper invoiceMapper;

    @Override
    public void configure() {

        restConfiguration()
                .bindingMode(RestBindingMode.json)
                .enableCORS(true)
                .dataFormatProperty("prettyPrint", "true");

        onException(Exception.class)
                .handled(true)
                .process(errorHandlerProcessor)
                .log(LoggingLevel.ERROR, "Handled exception: ${exception.message}");


        rest("/test")
            .get()
            .produces(MediaType.APPLICATION_JSON_VALUE)
            .to("direct:test");


        from("direct:test")
            .to("https://apachecamel.free.beeceptor.com/?bridgeEndpoint=true")
            .unmarshal()
            .json(JsonLibrary.Jackson, InvoiceDTO.class)
            .process(exchange -> {
                    var invoiceDTO = exchange.getIn().getBody(InvoiceDTO.class);
                    var summaryDTO = invoiceMapper.toSummaryDTO(invoiceDTO);
                    exchange.getIn().setBody(summaryDTO);
                    exchange.getIn().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                });


        /*from("direct:test")
            .to("https://apachecamel.free.beeceptor.com/?bridgeEndpoint=true")
            .unmarshal()
            .json(JsonLibrary.Jackson, InvoiceDTO.class)
            .setBody().body(InvoiceDTO.class, invoiceMapper::toSummaryDTO)
            .marshal()
            .json(JsonLibrary.Jackson, InvoiceSummaryDTO.class);*/
    }
}
