package com.wangzaiplus.test.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SearchFormDto {

    private List<FundTypeDto> typeList;
    private List<FundYieldDto> yieldList;
    private List<FundRankDto> rankList;

}
