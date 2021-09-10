package com.cas.controlcenter.controller;

import com.cas.controlcenter.controller.service.EnvironmentsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@Slf4j
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EnvironmentsController {

    @NonNull
    private final EnvironmentsService service;

    @GetMapping(value = "/envs", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EnvInfo> getEnvs() {
        return service.getEnvs();
    }

    @GetMapping(value = "/envs/{env}/details", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getEnv(@PathVariable String env) {
        return service.getEnvServicesDetail(env);
    }

}
