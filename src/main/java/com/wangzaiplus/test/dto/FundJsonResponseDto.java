package com.wangzaiplus.test.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FundJsonResponseDto {

    private Integer total;
    private Integer pageIndex;
    private String data;

}
