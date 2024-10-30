package org.adrewdev.apachecamel.exercise0.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.adrewdev.apachecamel.exercise0.model.Param;
import org.apache.camel.ProducerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CamelController {

    final ProducerTemplate producerTemplate;

    @GetMapping("/test1")
    public String test1() {
        val res = producerTemplate.send("direct:camel-route",
                p -> log.info("body= {}", p.getIn().getBody()));
        return res.getIn().getBody().toString();
    }

    @PostMapping("/api2")
    public ResponseEntity<?> api2(@RequestBody Param param) {
        log.info("api2 param= {}", param);
        return ResponseEntity.ok(param);
    }
}
