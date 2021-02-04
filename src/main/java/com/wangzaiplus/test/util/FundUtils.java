package com.wangzaiplus.test.util;

import com.google.common.collect.Lists;
import com.wangzaiplus.test.common.Constant;
import com.wangzaiplus.test.dto.FundDto;
import com.wangzaiplus.test.dto.FundJsonResponseDto;
import com.wangzaiplus.test.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FundUtils {

    private static final String BODY = "tbody";
    private static final String TR = "tr";
    private static final String TD = "td";
    private static final String COMMA = ",";
    private static final String QUESTION_MARK = "?";
    private static final String COLS_NUM = "3,4,6,8,9,10,11";
    private static final String PARAM_T = "t=";
    private static final String PARAM_PAGE = "&page=1&psize=50000";

    public static final String FUND_URL = "http://fund.eastmoney.com/api/Dtshph.ashx";

    public static List<FundDto> getFundDtoList(Integer type) {
        if (!Constant.FundType.contains(type)) {
            throw new ServiceException("type error: " + type);
        }

        List<List<String>> lists = getFundData(type);
        if (CollectionUtils.isEmpty(lists)) {
            return null;
        }

        return lists.stream().map(list -> {
            FundDto dto = FundDto.builder().type(type).build();
            try {
                BeanUtils.convert(list, dto);
            } catch (Exception e) {
                log.error("BeanUtils.convert error: " + e.getMessage(), e);
            }
            return dto;
        }).collect(Collectors.toList());
    }

    public static List<List<String>> getFundData(Integer type) {
        if (!Constant.FundType.contains(type)) {
            throw new ServiceException("type error: " + type);
        }

        OkHttpUtils httpUtils = SpringContextUtil.getBean(OkHttpUtils.class);
        try {
            String response = httpUtils.doGet(FUND_URL + QUESTION_MARK + PARAM_T + type + PARAM_PAGE);
            List<List<String>> lists = parseTable(response);
            return extract(lists, COLS_NUM);
        } catch (Exception e) {
            log.error("request fund data error: " + e.getMessage(), e);
            return null;
        }
    }

    public static List<List<String>> parse(String string) {
        String json = getJsonFromResponse(string);
        String data = getData(json);
        return extract(parseTable(data), COLS_NUM);
    }

    private static String getJsonFromResponse(String response) {
        if (StringUtils.isBlank(response)) {
            log.info("response is blank");
            return null;
        }

        List<String> list = extractMessage(response);
        if (CollectionUtils.isEmpty(list)) {
            log.info("extractFromBracket empty");
            return null;
        }

        return list.get(Constant.INDEX_ZERO);
    }

    private static String getData(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        FundJsonResponseDto dto = JsonUtil.strToObj(json, FundJsonResponseDto.class);
        if (null == dto) {
            return null;
        }

        return dto.getData();
    }

    /**
     * 提取中括号中内容，忽略中括号中的中括号
     * @param msg
     * @return
     */
    public static List<String> extractMessage(String msg) {
        List<String> list = new ArrayList<>();
        int start = 0;
        int startFlag = 0;
        int endFlag = 0;
        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) == '(') {
                startFlag++;
                if (startFlag == endFlag + 1) {
                    start = i;
                }
            } else if (msg.charAt(i) == ')') {
                endFlag++;
                if (endFlag == startFlag) {
                    list.add(msg.substring(start + 1, i));
                }
            }
        }
        return list;
    }

    /**
     * 解析table中td元素
     * @param data
     * @return
     */
    public static List<List<String>> parseTable(String data) {
        if (StringUtils.isBlank(data)) {
            return null;
        }

        ByteArrayInputStream is = new ByteArrayInputStream(data.getBytes());
        Document document;
        try {
            document = new SAXBuilder().build(is);
        } catch (Exception e) {
            log.error("build error, " + e.getMessage(), e);
            return null;
        }

        Element root = document.getRootElement();
        List<Element> bodyList = root.getChildren(BODY);
        if (CollectionUtils.isEmpty(bodyList)) {
            log.info("body empty");
            return null;
        }

        Element element = bodyList.get(Constant.INDEX_ZERO);
        List<Element> trList = element.getChildren(TR);
        if (CollectionUtils.isEmpty(trList)) {
            log.info("tr empty");
            return null;
        }

        List<List<String>> parentList = Lists.newArrayList();
        for (Element tr : trList) {
            List<Element> tdList = tr.getChildren(TD);
            if (CollectionUtils.isEmpty(tdList)) {
                log.info("td empty");
                continue;
            }

            List<String> childrenList = Lists.newArrayList();
            for (Element td : tdList) {
                childrenList.add(td.getValue());
            }

            parentList.add(childrenList);
        }

        return parentList;
    }

    /**
     * 提取指定列
     * @param lists
     * @param colsNum
     * @return
     */
    private static List<List<String>> extract(List<List<String>> lists, String colsNum) {
        if (CollectionUtils.isEmpty(lists)) {
            return null;
        }

        for (List<String> list : lists) {
            if (!checkCols(colsNum, list.size())) {
                return null;
            }
        }

        String[] split = colsNum.split(COMMA);
        List<List<String>> parentList = Lists.newArrayList();
        for (List<String> list : lists) {
            List<String> childrenList = Lists.newArrayList();
            for (String s : split) {
                Integer colNum = Integer.valueOf(s.trim());
                Integer colIndex = colNum - Constant.NUMBER_ONE;
                childrenList.add(list.get(colIndex));
            }
            parentList.add(childrenList);
        }

        return parentList;
    }

    /**
     * 校验列序号是否合法
     * @param colsNum 如：1,2,5,8
     * @param size
     * @return
     */
    private static boolean checkCols(String colsNum, int size) {
        if (StringUtils.isBlank(colsNum)) {
            return false;
        }

        String[] split = colsNum.split(COMMA);
        for (String col : split) {
            Integer colNum;
            try {
                colNum = Integer.valueOf(col.trim());
            } catch (Exception e) {
                log.error("Integer.valueOf error, " + e.getMessage(), e);
                return false;
            }

            if (colNum < Constant.NUMBER_ONE) {
                log.error("colNum can't be less than 1");
                return false;
            }

            if (colNum > size) {
                log.error("colNum: {} can't be greater than colSize: {}", colNum, size);
                return false;
            }
        }

        return true;
    }

}
