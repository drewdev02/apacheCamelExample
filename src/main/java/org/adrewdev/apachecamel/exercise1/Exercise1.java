package org.adrewdev.apachecamel.exercise1;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Exercise1 extends RouteBuilder {
    @Override
    public void configure() {

        restConfiguration().bindingMode(RestBindingMode.json);

        // endpoint para recibir un dato por path param
        rest("/api")
            .get("/pathparam/{data}")
            .produces(MediaType.APPLICATION_JSON_VALUE)
            .to("direct:pathParamRoute");

        from("direct:pathParamRoute")
            .log("Received request for path parameter route with data: ${header.data}")
            .choice()
                .when(header("data").isNull())
                    .process(exchange -> {
                    var errorResponse = new HashMap<>();
                    errorResponse.put("error", "Missing path parameter 'data'");
                    exchange.getIn().setBody(errorResponse);
                    exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
                })
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
                    .process(exchange -> {
                    var errorResponse = Map.of(
                            "error", "Missing query parameter 'param'"
                    );
                    exchange.getIn().setBody(errorResponse);
                    exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
                })
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
                    .process(exchange -> {
                    var errorResponse = Map.of(
                            "error", "Missing JSON body"
                    );
                    exchange.getIn().setBody(errorResponse);
                    exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
                })
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
