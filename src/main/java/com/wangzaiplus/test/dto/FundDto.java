package com.wangzaiplus.test.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FundDto {

    private String code;
    private String name;
    private String earningOf1;
    private String earningOf2;
    private String earningOf3;
    private String earningOf5;

}
