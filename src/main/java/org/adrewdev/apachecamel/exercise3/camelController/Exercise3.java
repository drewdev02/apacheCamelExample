package org.adrewdev.apachecamel.exercise3.camelController;

import lombok.RequiredArgsConstructor;
import org.adrewdev.apachecamel.exercise3.dto.InvoiceMapper;
import org.adrewdev.apachecamel.exercise3.dto.ResponseData;
import org.adrewdev.apachecamel.exercise3.errors.ErrorHandlerProcessor;
import org.adrewdev.apachecamel.exercise3.validations.Validate;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Exercise3 extends RouteBuilder {

    private final ErrorHandlerProcessor errorHandlerProcessor;
    private final InvoiceMapper invoiceMapper;

    @Value("${mock.server.url}")
    private String mockServerUrl;

    @Override
    public void configure() {
        System.out.println(mockServerUrl);

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
            .to(mockServerUrl + "/db?bridgeEndpoint=true")
            .unmarshal()
            .json(JsonLibrary.Jackson, ResponseData.class)
            .process(exchange -> {
                    var res = (ResponseData) exchange.getIn().getBody();
                    Validate.validateInvoice(exchange, res);
                    var summaryDTO = invoiceMapper.toSummaryDTO(res);
                    exchange.getIn().setBody(summaryDTO);
                    exchange.getIn().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                });
    }
}
