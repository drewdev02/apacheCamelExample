package org.adrewdev.apachecamel.exercise0.camelController;

import org.adrewdev.apachecamel.exercise0.model.Param;
import org.adrewdev.apachecamel.exercise0.model.UserDto;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class Exercise0 extends RouteBuilder {
    @Override
    public void configure() {

        rest("/")
            .bindingMode(RestBindingMode.auto)
            .get("test2")
            .to("direct:camel-route");

        from("direct:camel-route")
            .to("https://jsonplaceholder.typicode.com/users/1?bridgeEndpoint=true&httpMethod=GET")
            .unmarshal()
            .json(JsonLibrary.Jackson, UserDto.class)
            .setBody().body(UserDto.class, userDto ->
                        Param.builder()
                                .value(userDto)
                                .build())
            .marshal()
            .json(JsonLibrary.Jackson, Param.class)
            .to("http://localhost:8080/api2?bridgeEndpoint=true&httpMethod=POST")
            .unmarshal()
            .json();
    }
}