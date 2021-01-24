package com.wangzaiplus.test.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wangzaiplus.test.common.Constant;
import com.wangzaiplus.test.common.ResponseCode;
import com.wangzaiplus.test.common.ServerResponse;
import com.wangzaiplus.test.dto.*;
import com.wangzaiplus.test.exception.ServiceException;
import com.wangzaiplus.test.mapper.FundMapper;
import com.wangzaiplus.test.pojo.Fund;
import com.wangzaiplus.test.service.FundService;
import com.wangzaiplus.test.util.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FundServiceImpl implements FundService {

    @Autowired
    private FundMapper fundMapper;

    @Override
    public ServerResponse search(SearchFormDto searchFormDto) {
        check(searchFormDto);

        List<List<FundDto>> lists = Lists.newArrayList();
        List<FundYieldDto> yieldList = searchFormDto.getYieldList();
        for (FundYieldDto yield : yieldList) {
            FundDto dto = FundDto.builder()
                    .type(searchFormDto.getTypeList().get(Constant.INDEX_ZERO).getType())
                    .orderBy(yield.getYield())
                    .build();
            List<Fund> fundList = fundMapper.selectByType(dto);
            if (CollectionUtils.isNotEmpty(fundList)) {
                lists.add(toFundDtoList(fundList));
            }
        }

        ListUtils listUtils = new ListUtils<FundDto>();
        List intersection = listUtils.intersection(lists, true);

        return ServerResponse.success(intersection);
    }

    private void check(SearchFormDto searchFormDto) {
        if (null == searchFormDto) {
            throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        }

        List<FundTypeDto> typeList = searchFormDto.getTypeList();
        List<FundYieldDto> yieldList = searchFormDto.getYieldList();
        List<FundRankDto> rankList = searchFormDto.getRankList();
        if (CollectionUtils.isEmpty(typeList)
                || CollectionUtils.isEmpty(yieldList)
                || CollectionUtils.isEmpty(rankList)) {
            throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        }
    }

    @Override
    public void update(List<FundDto> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        for (FundDto fundDto : list) {
            Fund fund = toFund(fundDto);
            // TODO lock
            Fund oldFund = fundMapper.selectByCodeAndType(fund);
            if (null == oldFund) {
                fundMapper.insert(fund);
            } else {
                fund.setId(oldFund.getId());
                fundMapper.update(fund);
            }
        }
    }

    @Override
    public ServerResponse rank(FundDto fundDto) {
        List<Fund> fundList = fundMapper.selectByNameOrCode(fundDto);
        return ServerResponse.success(toFundDtoList(fundList));
    }

    private Fund toFund(FundDto fundDto) {
        Fund fund = Fund.builder().build();
        BeanUtils.copyProperties(fundDto, fund);
        return fund;
    }

    private List<FundDto> toFundDtoList(List<Fund> fundList) {
        if (CollectionUtils.isEmpty(fundList)) {
            return null;
        }

        return fundList.stream().map(fund -> {
            FundDto fundDto = FundDto.builder().build();
            BeanUtils.copyProperties(fund, fundDto);
            return fundDto;
        }).collect(Collectors.toList());
    }

    @Override
    public ServerResponse getSearchFormDto() {
        SearchFormDto searchFormDto = SearchFormDto.builder()
                .typeList(Constant.FundType.getTypeList())
                .yieldList(Constant.FundYield.getYieldList())
                .rankList(Constant.FundRank.getRankList())
                .build();
        return ServerResponse.success(searchFormDto);
    }

    public Map<String, List<FundDto>> loadFundListMap() {
        List<FundTypeDto> typeList = Constant.FundType.getTypeList();
        List<FundYieldDto> yieldList = Constant.FundYield.getYieldList();
        if (CollectionUtils.isEmpty(typeList) || CollectionUtils.isEmpty(yieldList)) {
            throw new ServiceException("typeList or yieldList can not be empty");
        }

        Map<String, List<FundDto>> map = Maps.newHashMap();
        typeList.stream().forEach(type -> {
            yieldList.stream().forEach(yield -> {
                Integer fundType = type.getType();
                String fundYield = yield.getYield();

                FundDto build = FundDto.builder().type(fundType).orderBy(fundYield).build();
                List<Fund> fundList = fundMapper.selectByType(build);
                if (CollectionUtils.isEmpty(fundList)) {
                    return;
                }

                String key = getFundListRedisKey(fundType, fundYield);
                List<FundDto> value = toFundDtoList(fundList);
                map.put(key, value);
            });
        });

        return map;
    }

    public Map<String, Integer> loadFundRankMap() {
        Map<String, List<FundDto>> fundListMap = loadFundListMap();
        if (null == fundListMap || fundListMap.size() == 0) {
            return null;
        }

        Map<String, Integer> map = Maps.newHashMap();
        fundListMap.forEach((key, value) -> value.stream().forEach(dto -> map.put(getFundRankRedisKey(key, dto.getId()), dto.getTempRank())));

        return map;
    }

    private String getFundRankRedisKey(String key, Integer id) {
        return key.replace(Constant.Redis.FUND_LIST, Constant.Redis.FUND_RANK) + Constant.COLON + id;
    }

    private String getFundListRedisKey(Integer type, String yield) {
        return Constant.Redis.FUND_LIST + Constant.COLON + type + Constant.COLON + yield;
    }

}
