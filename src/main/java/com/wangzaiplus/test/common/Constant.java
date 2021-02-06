package com.wangzaiplus.test.common;

import com.wangzaiplus.test.dto.FundRankDto;
import com.wangzaiplus.test.dto.FundTypeDto;
import com.wangzaiplus.test.dto.FundYieldDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Constant {

    public static final int MAX_SIZE_PER_TIME = 1000;
    public static final int INDEX_ZERO = 0;
    public static final int INDEX_ONE = 1;
    public static final int INDEX_TWO = 2;
    public static final int INDEX_THREE = 3;

    public static final int NUMBER_ZERO = 0;
    public static final int NUMBER_ONE = 1;

    public static final String COLON = ":";
    public static final String COMMA = ",";
    public static final String DOUBLE_STRIGULA = "--";
    public static final String REPLACEMENT_TARGET = "-99999%";

    public static final String UNKNOWN_TYPE = "未知类型";

    public interface Redis {
        String OK = "OK";
        // 过期时间, 60s, 一分钟
        Integer EXPIRE_TIME_MINUTE = 60;
        // 过期时间, 一小时
        Integer EXPIRE_TIME_HOUR = 60 * 60;
        // 过期时间, 一天
        Integer EXPIRE_TIME_DAY = 60 * 60 * 24;
        String TOKEN_PREFIX = "token:";
        String MSG_CONSUMER_PREFIX = "consumer:";
        String ACCESS_LIMIT_PREFIX = "accessLimit:";
        String FUND_RANK = "fundRank";
        String FUND_LIST = "fundList";
    }

    public interface LogType {
        // 登录
        Integer LOGIN = 1;
        // 登出
        Integer LOGOUT = 2;
    }

    public interface MsgLogStatus {
        // 消息投递中
        Integer DELIVERING = 0;
        // 投递成功
        Integer DELIVER_SUCCESS = 1;
        // 投递失败
        Integer DELIVER_FAIL = 2;
        // 已消费
        Integer CONSUMED_SUCCESS = 3;
    }

    public enum CalculateTypeEnum {
        ADD(1, "加"),
        SUBTRACT(2, "减"),
        MULTIPLY(3, "乘"),
        DIVIDE(4, "除")
        ;

        Integer type;
        String desc;

        CalculateTypeEnum(Integer type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public Integer getType() {
            return type;
        }

        public String getDesc() {
            return desc;
        }
    }

    public enum FundType {
        STOCK_FUND(1, "股票型"),
        HYBRID_FUND(2, "混合型"),
        BOND_FUND(3, "债券型"),
        INDEX_FUND(4, "指数型"),
        QDII(5, "QDII")
        ;

        private Integer type;
        private String desc;

        FundType(Integer type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public Integer getType() {
            return type;
        }

        public String getDesc() {
            return desc;
        }

        public static boolean contains(Integer type) {
            FundType[] values = FundType.values();
            return Arrays.stream(values).filter(fundType -> fundType.getType() == type).findAny().isPresent();
        }

        public static List<FundTypeDto> getTypeList() {
            FundType[] values = FundType.values();
            return Arrays.stream(values).map(fundType ->
                FundTypeDto.builder()
                    .type(fundType.getType())
                    .desc(fundType.getDesc())
                    .build()
            ).collect(Collectors.toList());
        }

        public static String getDescByType(Integer type) {
            FundType[] values = FundType.values();
            Optional<FundType> first = Arrays.stream(values).filter(fundType -> fundType.getType() == type).findFirst();
            boolean exists = first.isPresent();
            return exists ? first.get().getDesc() : UNKNOWN_TYPE;
        }
    }

    public enum FundYield {
        YIELD_OF_ONE_YEAR("yield_of_one_year", "近1年收益率"),
        YIELD_OF_TWO_YEAR("yield_of_two_year", "近2年收益率"),
        YIELD_OF_THREE_YEAR("yield_of_three_year", "近3年收益率"),
        YIELD_OF_FIVE_YEAR("yield_of_five_year", "近5年收益率")
        ;

        private String yield;
        private String desc;

        FundYield(String yield, String desc) {
            this.yield = yield;
            this.desc = desc;
        }

        public String getYield() {
            return yield;
        }

        public String getDesc() {
            return desc;
        }

        public static boolean contains(String yield) {
            FundYield[] values = FundYield.values();
            return Arrays.stream(values).filter(fundYield -> fundYield.getYield().equals(yield)).findAny().isPresent();
        }

        public static List<FundYieldDto> getYieldList() {
            FundYield[] values = FundYield.values();
            return Arrays.stream(values).map(fundYield ->
                FundYieldDto.builder()
                    .yield(fundYield.getYield())
                    .desc(fundYield.getDesc())
                    .build()
            ).collect(Collectors.toList());
        }
    }

    public enum FundRank {
        TOP_50(50, "前50"),
        TOP_100(100, "前100"),
        TOP_150(150, "前150"),
        TOP_200(200, "前200"),
        ;

        private Integer rank;
        private String desc;

        FundRank(Integer rank, String desc) {
            this.rank = rank;
            this.desc = desc;
        }

        public Integer getRank() {
            return rank;
        }

        public String getDesc() {
            return desc;
        }

        public static List<FundRankDto> getRankList() {
            FundRank[] values = FundRank.values();
            return Arrays.stream(values).map(rank ->
                    FundRankDto.builder()
                            .rank(rank.getRank())
                            .desc(rank.getDesc())
                            .build()
            ).collect(Collectors.toList());
        }
    }

    public enum FundSortType {
        ASC("asc"),
        DESC("desc"),
        ;

        private String type;

        FundSortType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

}
