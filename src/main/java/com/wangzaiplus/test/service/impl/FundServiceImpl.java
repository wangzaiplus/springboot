package com.wangzaiplus.test.service.impl;

import com.google.common.collect.Lists;
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

import java.util.Arrays;
import java.util.List;
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
                    .sort(Constant.FundSortType.DESC.getType())
                    .limit(searchFormDto.getRankList().get(Constant.INDEX_ZERO).getRank())
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
    public List<FundDto> rank(FundDto fundDto) {
        List<Fund> fundList = fundMapper.selectByType(fundDto);
        return toFundDtoList(fundList);
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

}
