package com.wangzaiplus.test.service.strategy;

import com.wangzaiplus.test.common.Constant;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("multiplyCalculateServiceImpl")
public class MultiplyCalculateServiceImpl implements CalculateService, InitializingBean {

    @Override
    public int calculate(int a, int b) {
        return a * b;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CalculateStrategy.register(Constant.CalculateTypeEnum.MULTIPLY.getType(), this);
    }

}
