package com.cas.controlcenter.controller;

import com.cas.controlcenter.controller.service.EnvironmentsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;


@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
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

    @GetMapping(value = "/envs/block")
    public List<EnvInfo> getEnvsBlock() {
        return service.getEnvs().collectList().toProcessor().block();
    }

}
