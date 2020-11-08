package com.wangzaiplus.test.service.strategy;

import com.wangzaiplus.test.common.Constant;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("multiplyCalculateServiceImpl")
public class MultiplyCalculateServiceImpl implements CalculateService, InitializingBean {

    @Autowired
    private CalculateStrategy calculateStrategy;

    @Override
    public int calculate(int a, int b) {
        return a * b;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        calculateStrategy.register(Constant.CalculateTypeEnum.MULTIPLY.getType(), this);
    }

}
