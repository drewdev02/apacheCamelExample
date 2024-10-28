package org.adrewdev.apachecamel.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.adrewdev.apachecamel.model.Param;
import org.adrewdev.apachecamel.model.UserDto;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelConfig extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:camel-route")
                .to("https://jsonplaceholder.typicode.com/users/1?bridgeEndpoint=true")
                .process(p -> {
                    val mapper = new ObjectMapper();
                    val body = p.getIn().getBody(String.class);
                    val userDto = mapper.readValue(body, UserDto.class);
                    val param = Param.builder()
                            .value(userDto)
                            .build();
                    val json = mapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(param);
                    p.getIn().setBody(json);
                })
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("http://localhost:8080/api2?bridgeEndpoint=true");
    }
}