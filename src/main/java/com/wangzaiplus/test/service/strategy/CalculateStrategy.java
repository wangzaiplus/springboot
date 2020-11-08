package com.wangzaiplus.test.service.strategy;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CalculateStrategy {

    private static Map<Integer, CalculateService> map = Maps.newConcurrentMap();

    public void register(Integer type, CalculateService calculateService) {
        map.put(type, calculateService);
    }

    public CalculateService getCalculateService(Integer type) {
        return map.get(type);
    }

}
