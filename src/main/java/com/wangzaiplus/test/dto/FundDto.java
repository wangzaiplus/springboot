package com.wangzaiplus.test.dto;

import lombok.*;

import java.util.Objects;

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
