package org.adrewdev.apachecamel.exercise1.camelController;

import lombok.RequiredArgsConstructor;
import org.adrewdev.apachecamel.exercise1.errors.ErrorHandlerProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class Exercise1 extends RouteBuilder {

    private final ErrorHandlerProcessor errorHandlerProcessor;

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

        // endpoint para recibir un dato por path param
        rest("/api")
            .get("/pathparam/{data}")
            .produces(MediaType.APPLICATION_JSON_VALUE)
            .to("direct:pathParamRoute");

        from("direct:pathParamRoute")
            .log("Received request for path parameter route with data: ${header.data}")
            .choice()
                .when(header("data").isNull())
                    .throwException(new IllegalArgumentException("Missing path parameter 'data'"))
                .otherwise()
                    .process(exchange -> {
                    var response = Map.of(
                            "message", "Received path param",
                            "data", exchange.getIn().getHeader("data")
                    );
                    exchange.getIn().setBody(response);
                    exchange.getIn().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                });


        // endpoint para recibir un dato por query param
        rest("/api")
            .get("/queryparam")
            .produces(MediaType.APPLICATION_JSON_VALUE)
            .to("direct:queryParamRoute");

        from("direct:queryParamRoute")
            .log("Received request for query parameter route with param: ${header.param}")
            .choice()
                .when(header("param").isNull())
                    .throwException(new IllegalArgumentException("Missing query parameter 'param'"))
                .otherwise()
                    .process(exchange -> {
                    var response = Map.of(
                            "message", "Received query param",
                            "param", exchange.getIn().getHeader("param")
                    );
                    exchange.getIn().setBody(response);
                    exchange.getIn().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                });

        // endpoint para recibir un JSON en el cuerpo del POST
        rest("/api")
            .post("/jsonbody")
            .consumes(MediaType.APPLICATION_JSON_VALUE)
            .produces(MediaType.APPLICATION_JSON_VALUE)
            .to("direct:jsonBodyRoute");

        from("direct:jsonBodyRoute")
            .log("Received request for JSON body route")
            .choice()
                .when(body().isNull())
                    .throwException(new IllegalArgumentException("Missing JSON body"))
                .otherwise()
                    .process(exchange -> {
                    var json = exchange.getIn().getBody(Map.class);
                    var response = Map.of(
                            "message", "Received JSON body",
                            "data", json
                    );
                    exchange.getIn().setBody(response);
                });
    }
}
