package com.wangzaiplus.test.service.strategy;

import com.wangzaiplus.test.common.Constant;
import com.wangzaiplus.test.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CalculateServiceFactory {

    @Autowired
    @Qualifier("addCalculateServiceImpl")
    private CalculateService addCalculateServiceImpl;

    @Autowired
    @Qualifier("subtractCalculateServiceImpl")
    private CalculateService subtractCalculateServiceImpl;

    @Autowired
    @Qualifier("multiplyCalculateServiceImpl")
    private CalculateService multiplyCalculateServiceImpl;

    @Autowired
    @Qualifier("divideCalculateServiceImpl")
    private CalculateService divideCalculateServiceImpl;

    public CalculateService getCalculateService(Integer type) {
        CalculateService calculateService;

        if (Constant.CalculateTypeEnum.ADD.getType().equals(type)) {
            calculateService = addCalculateServiceImpl;
        } else if (Constant.CalculateTypeEnum.SUBTRACT.getType().equals(type)) {
            calculateService = subtractCalculateServiceImpl;
        } else if (Constant.CalculateTypeEnum.SUBTRACT.getType().equals(type)) {
            calculateService = multiplyCalculateServiceImpl;
        } else if (Constant.CalculateTypeEnum.SUBTRACT.getType().equals(type)) {
            calculateService = divideCalculateServiceImpl;
        } else {
            throw new ServiceException("type error: " + type);
        }

        return calculateService;
    }

}
