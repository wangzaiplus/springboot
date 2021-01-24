package com.wangzaiplus.test.pojo;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Fund {

    private Integer id;
    private String code;
    private String name;
    private BigDecimal netValue;
    private String yieldOfOneYear;
    private String yieldOfTwoYear;
    private String yieldOfThreeYear;
    private String yieldOfFiveYear;
    private int type;
    private Date establishedTime;
    private String asset;
    private String manager;
    private Integer status;
    private Integer isDeleted;
    private Date createdTime;
    private Date updatedTime;

    private Integer tempRank;

}
