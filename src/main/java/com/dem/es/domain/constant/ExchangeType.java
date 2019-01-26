package com.dem.es.domain.constant;

import lombok.Getter;

@Getter
public enum ExchangeType {
    FANOUT("fanout"),
    DIRECT("direct"),
    TOPIC("topic"),
    ;
    private String name;

    ExchangeType(String name) {
        this.name = name;
    }

}
