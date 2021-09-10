package com.cas.controlcenter.controller.service;

import com.cas.controlcenter.controller.EnvInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EnvironmentsServiceImpl implements EnvironmentsService {

    @Override
    public Flux<EnvInfo> getEnvs() {

        return Flux.interval(Duration.ofMillis(500))
                .take(5)
                .map(it -> EnvInfo.builder()
                        .branch("env-" + it)
                        .owner("James Z")
                        .services(List.of("mcsv-1", "mcsv-2", "mcsv-3"))
                        .build()
                );
    }

    @Override
    public Flux<String> getEnvServicesDetail(String env) {
        return Flux.fromIterable(List.of("mcsv-1:0.1.0", "mcsv-1:0.2.0-SNAPSHOT", "mcsv-2:0.5.1", "mcsv-3:1.0.2"));
    }
}
