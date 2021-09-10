package com.cas.controlcenter.controller.service;

import com.cas.controlcenter.controller.EnvInfo;
import reactor.core.publisher.Flux;

public interface EnvironmentsService {

    Flux<EnvInfo> getEnvs();

    Flux<String> getEnvServicesDetail(String env);
}
