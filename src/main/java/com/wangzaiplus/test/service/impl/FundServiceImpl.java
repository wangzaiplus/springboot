package com.wangzaiplus.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import com.wangzaiplus.test.util.JedisUtil;
import com.wangzaiplus.test.util.ListUtils;
import com.wangzaiplus.test.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FundServiceImpl implements FundService {

    @Autowired
    private FundMapper fundMapper;

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public ServerResponse search(SearchFormDto searchFormDto) {
        check(searchFormDto);

        List<List<FundDto>> lists = Lists.newArrayList();
        List<FundYieldDto> yieldList = searchFormDto.getYieldList();
        for (FundYieldDto yield : yieldList) {
            FundDto dto = FundDto.builder()
                    .type(searchFormDto.getTypeList().get(Constant.INDEX_ZERO).getType())
                    .orderBy(yield.getYield())
                    .limit(searchFormDto.getRankList().get(Constant.INDEX_ZERO).getRank())
                    .build();
            List<Fund> fundList = fundMapper.selectByType(dto);
            if (CollectionUtils.isNotEmpty(fundList)) {
                lists.add(toFundDtoList(fundList));
            }
        }

        ListUtils listUtils = new ListUtils<FundDto>();
        List intersection = listUtils.intersection(lists, true);

        return ServerResponse.success(addRankInfo(intersection));
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
        checkPageInfo(fundDto);

        PageHelper.startPage(fundDto.getPageNum(), fundDto.getPageSize());
        List<Fund> fundList = fundMapper.selectByNameOrCode(fundDto);
        PageInfo<Fund> pageInfo = new PageInfo<>(fundList);

        List<Fund> pageInfoList = pageInfo.getList();
        if (CollectionUtils.isEmpty(pageInfoList)) {
            return ServerResponse.success();
        }

        List<FundDto> fundDtoList = toFundDtoList(pageInfoList);
        List<FundDto> list = addRankInfo(fundDtoList);

        return ServerResponse.success(PageUtils.merge(pageInfo, list));
    }

    private void checkPageInfo(FundDto fundDto) {
        boolean paramIsNull = null == fundDto || null == fundDto.getPageNum() || null == fundDto.getPageSize();
        if (paramIsNull) {
            throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        }

        boolean paramIsIllegal = fundDto.getPageNum() < Constant.NUMBER_ONE || fundDto.getPageSize() < Constant.NUMBER_ONE;
        if (paramIsIllegal) {
            throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        }
    }

    private List<FundDto> addRankInfo(List<FundDto> fundDtoList) {
        return fundDtoList.stream().map(dto -> {
            int type = dto.getType();
            Integer id = dto.getId();

            List<String> rankList = getRank(type, id);

            String rankOne = Constant.DOUBLE_STRIGULA.equals(dto.getYieldOfOneYear())
                    ? Constant.DOUBLE_STRIGULA : rankList.get(Constant.INDEX_ZERO);
            String rankTwo = Constant.DOUBLE_STRIGULA.equals(dto.getYieldOfTwoYear())
                    ? Constant.DOUBLE_STRIGULA : rankList.get(Constant.INDEX_ONE);
            String rankThree = Constant.DOUBLE_STRIGULA.equals(dto.getYieldOfThreeYear())
                    ? Constant.DOUBLE_STRIGULA : rankList.get(Constant.INDEX_TWO);
            String rankFive = Constant.DOUBLE_STRIGULA.equals(dto.getYieldOfFiveYear())
                    ? Constant.DOUBLE_STRIGULA : rankList.get(Constant.INDEX_THREE);

            dto.setRankOne(rankOne);
            dto.setRankTwo(rankTwo);
            dto.setRankThree(rankThree);
            dto.setRankFive(rankFive);

            return dto;
        }).collect(Collectors.toList());
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
            fundDto.setTypeDesc(Constant.FundType.getDescByType(fundDto.getType()));
            return fundDto;
        }).collect(Collectors.toList());
    }

    private List<String> getRank(Integer type, Integer id) {
        String prefix = getFundRankPrefix(type, id);

        String rankStr = jedisUtil.get(prefix);
        if (StringUtils.isNotBlank(rankStr)) {
            return toRankList(rankStr);
        }

        Map<String, String> map = loadFundRankMapByType(type);
        if (null == map || map.size() == 0) {
            return emptyRankList();
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (prefix.equals(key)) {
                return toRankList(value);
            }
        }

        return emptyRankList();
    }

    private List<String> toRankList(String rankStr) {
        if (StringUtils.isBlank(rankStr)) {
            return emptyRankList();
        }

        String[] split = rankStr.split(Constant.COMMA);
        if (null == split || split.length != 4) {
            log.error("error rankStr: {}", rankStr);
            return emptyRankList();
        }

        return Arrays.asList(split);
    }

    private List<String> emptyRankList() {
        return Lists.newArrayList(Constant.DOUBLE_STRIGULA, Constant.DOUBLE_STRIGULA, Constant.DOUBLE_STRIGULA, Constant.DOUBLE_STRIGULA);
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

    public Map<String, String> loadFundRankMapByType(Integer type) {
        if (!Constant.FundType.contains(type)) {
            log.error("type error: {}", type);
            return null;
        }

        List<FundYieldDto> yieldList = Constant.FundYield.getYieldList();
        if (CollectionUtils.isEmpty(yieldList)) {
            throw new ServiceException("yieldList can't be empty");
        }

        Map<String, String> map = Maps.newHashMap();
        yieldList.stream().forEach(yield -> {
            String fundYield = yield.getYield();

            FundDto build = FundDto.builder()
                    .type(type)
                    .orderBy(fundYield)
                    .replacementSource(Constant.DOUBLE_STRIGULA)
                    .replacementTarget(Constant.REPLACEMENT_TARGET)
                    .build();
            List<Fund> fundList = fundMapper.selectTempRankByType(build);
            if (CollectionUtils.isEmpty(fundList)) {
                return;
            }

            List<FundDto> list = toFundDtoList(fundList);
            list.stream().forEach(dto -> {
                Integer id = dto.getId();
                String key = getFundRankPrefix(type, id);
                String value = map.get(key);
                Integer tempRank = dto.getTempRank();

                if (StringUtils.isBlank(value)) {
                    map.put(key, String.valueOf(tempRank));
                } else {
                    value = value + Constant.COMMA + tempRank;
                    map.put(key, value);
                }
            });
        });

        map.forEach((key, value) -> jedisUtil.set(key, value));

        return map;
    }

    private String getFundRankPrefix(Integer type, Integer id) {
        return Constant.Redis.FUND_RANK + Constant.COLON + type + Constant.COLON + id;
    }

}
