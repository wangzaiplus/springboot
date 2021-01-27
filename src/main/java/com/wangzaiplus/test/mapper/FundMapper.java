package com.wangzaiplus.test.mapper;

import com.wangzaiplus.test.dto.FundDto;
import com.wangzaiplus.test.pojo.Fund;
import com.wangzaiplus.test.service.batch.BatchProcessMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FundMapper extends BatchProcessMapper<Fund> {

    List<Fund> selectAll();

    Fund selectOne(Integer id);

    void insert(Fund fund);

    void update(Fund fund);

    void delete(Integer id);

    Fund selectByCodeAndType(Fund fund);

    List<Fund> selectByType(FundDto fundDto);

    List<Fund> selectTempRankByType(FundDto fundDto);

    List<Integer> selectFundTypeList();

    List<Fund> selectByNameOrCode(FundDto fundDto);

}
