package com.wangzaiplus.test.dto;

import com.wangzaiplus.test.annotation.ColNum;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FundDto {

    @ColNum(colNum = 1)
    private String code;
    @ColNum(colNum = 2)
    private String name;
    @ColNum(colNum = 3)
    private String netValue;
    @ColNum(colNum = 4)
    private String earningOf1;
    @ColNum(colNum = 5)
    private String earningOf2;
    @ColNum(colNum = 6)
    private String earningOf3;
    @ColNum(colNum = 7)
    private String earningOf5;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof FundDto)) {
            return false;
        }

        FundDto dto = (FundDto) obj;
        return this.code.equals(dto.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }

}
