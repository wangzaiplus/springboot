package com.wangzaiplus.test.dto;

import com.wangzaiplus.test.annotation.ColNum;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FundDto {

    private Integer id;
    @ColNum(colNum = 1)
    private String code;
    @ColNum(colNum = 2)
    private String name;
    @ColNum(colNum = 3)
    private BigDecimal netValue;
    @ColNum(colNum = 4)
    private String yieldOfOneYear;
    @ColNum(colNum = 5)
    private String yieldOfTwoYear;
    @ColNum(colNum = 6)
    private String yieldOfThreeYear;
    @ColNum(colNum = 7)
    private String yieldOfFiveYear;
    private int type;
    private String typeDesc;
    private Date establishedTime;
    private String asset;
    private String manager;
    private Integer status;
    private Integer isDeleted;
    private Date createdTime;
    private Date updatedTime;

    private String orderBy;
    private String sort;
    private Integer limit;

    List<String> orderByList;

    private String nameOrCode;
    private Integer tempRank;
    private String rankOne;
    private String rankTwo;
    private String rankThree;
    private String rankFive;

    private Integer pageNum;
    private Integer pageSize;

    private String replacementSource;
    private String replacementTarget;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof FundDto)) {
            return false;
        }

        FundDto dto = (FundDto) obj;
        return Objects.equals(this.getCode(), dto.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }

}
