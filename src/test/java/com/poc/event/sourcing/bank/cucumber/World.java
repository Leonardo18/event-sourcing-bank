package com.poc.event.sourcing.bank.cucumber;

import com.google.common.collect.Maps;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("cucumber-glue")
public class World {
    public Map<String, Object> map = Maps.newHashMap();
    public Integer status;
    public String errorMessage;
}
