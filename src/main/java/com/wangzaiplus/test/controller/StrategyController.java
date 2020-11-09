package com.wangzaiplus.test.controller;

import com.wangzaiplus.test.common.Constant;
import com.wangzaiplus.test.common.ServerResponse;
import com.wangzaiplus.test.exception.ServiceException;
import com.wangzaiplus.test.pojo.CalculateDto;
import com.wangzaiplus.test.service.strategy.CalculateService;
import com.wangzaiplus.test.service.strategy.CalculateServiceFactory;
import com.wangzaiplus.test.service.strategy.CalculateStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class StrategyController {

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

    @Autowired
    private CalculateServiceFactory calculateServiceFactory;

    @PostMapping("strategy1")
    public ServerResponse strategy1(@RequestBody CalculateDto dto) {
        Integer type = dto.getType();
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

        int result = calculateService.calculate(dto.getA(), dto.getB());

        return ServerResponse.success(result);
    }

    @PostMapping("strategy2")
    public ServerResponse strategy2(@RequestBody CalculateDto dto) {
        CalculateService calculateService = getCalculateService(dto.getType());
        int result = calculateService.calculate(dto.getA(), dto.getB());
        return ServerResponse.success(result);
    }

    private CalculateService getCalculateService(Integer type) {
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

    @PostMapping("strategy3")
    public ServerResponse strategy3(@RequestBody CalculateDto dto) {
        CalculateService calculateService = calculateServiceFactory.getCalculateService(dto.getType());
        int result = calculateService.calculate(dto.getA(), dto.getB());
        return ServerResponse.success(result);
    }

    @PostMapping("strategy4")
    public ServerResponse strategy4(@RequestBody CalculateDto dto) {
        CalculateService calculateService = CalculateStrategy.getCalculateService(dto.getType());
        int result = calculateService.calculate(dto.getA(), dto.getB());
        return ServerResponse.success(result);
    }


}
