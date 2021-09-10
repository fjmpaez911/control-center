package com.cas.controlcenter.controller;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Builder
@ToString
public class EnvInfo {

    private String branch;
    private String owner;
    private List<String> services;

}
