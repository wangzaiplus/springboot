package com.wangzaiplus.test.util;

import com.google.common.collect.Lists;
import com.wangzaiplus.test.common.Constant;
import com.wangzaiplus.test.dto.FundJsonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class TableParseUtils {

    private static final String BODY = "tbody";
    private static final String TR = "tr";
    private static final String TD = "td";
    private static final String COMMA = ",";
    private static final String COLS_NUM = "3,4,8,9,10,11";

    public static void main(String[] args) {
        String table = "<table><thead><tr><th class=\"xz\">选择<br/>比较</th><th class=\"xh\">序号</th><th class=\"dm\"><a href=\"http://fund.eastmoney.com/dingtou/syph_gp_bzdm.html\" target=\"_self\">代码</a></th><th class=\"jc\"><a href=\"http://fund.eastmoney.com/dingtou/syph_gp_jjjc.html\" target=\"_self\">简称</a></th><th class=\"xglj\">相关链接</th><th class=\"dwjz\"><a href=\"http://fund.eastmoney.com/dingtou/syph_gp_dwjz.html\" target=\"_self\">单位净值</a></th><th class=\"rq\">日期</th><th class=\"yndt\"><a class=\"desc\">近1年<br />定投收益</a></th><th class=\"endt\"><a href=\"http://fund.eastmoney.com/dingtou/syph_gp_endt.html\" target=\"_self\">近2年<br />定投收益</a></th><th class=\"sndt\"><a href=\"http://fund.eastmoney.com/dingtou/syph_gp_sndt.html\" target=\"_self\">近3年<br />定投收益</a></th><th class=\"wndt\"><a href=\"http://fund.eastmoney.com/dingtou/syph_gp_wndt.html\" target=\"_self\">近5年<br />定投收益</a></th><th class=\"zhpj\"><a href=\"http://fund.eastmoney.com/dingtou/syph_gp_zhpj.html\" target=\"_self\">上海证券评级<br />09-30</a></th><th>手续费</th><th class=\"cz\"><span class='operate' onclick=\"canbuy()\"><input type=\"radio\" id=\"fund_buy\" checked='checked' runat=\"server\" name=\"fundstate\" value=\"1\" /><label class='dxjj' for=\"fund_buy\">可购</label></span><span class='operate' onclick=\"allfund()\"><input type=\"radio\" id=\"fund_all\"  runat=\"server\" name=\"fundstate\" value=\"0\" /><label class='dxjj' for=\"fund_all\">全部</label></span></th></tr></thead><tbody><tr><td><input type=\"checkbox\" value=\"004997,广发高端制造股票A\" id=\"chk004997\" onclick=\"_$fn_check(this)\" /></td><td>1</td><td><a href=\"/004997.html\">004997</a></td><td class=\"tl\"><a href=\"/004997.html\">广发高端制造股票A</a></td><td class=\"xglj\"><a href=\"/004997.html\" class=\"red\">估算图</a><a href=\"http://jijinba.eastmoney.com/list,004997.html\">基金吧</a><a href=\"http://fundf10.eastmoney.com/004997.html\">档案</a></td><td><span class=\"ping\">2.5121</span></td><td>11-13</td><td><span class=\"zhang\">73.00%</span></td><td><span class=\"zhang\">137.26%</span></td><td><span class=\"zhang\">148.56%</span></td><td>123.45%</td><td><span class=\"lgray\">暂无评级</span></td><td><span><a class=\"zkfl\" href='http://fundf10.eastmoney.com/jjfl_004997.html'>0.15%</a></span></td><td><a class='btn dingtou' href='https://trade.1234567.com.cn/Investment/add.aspx?fc=004997'>定投</a></td></tr><tr class=\"odd\"><td><input type=\"checkbox\" value=\"005968,创金合信工业周期股票A\" id=\"chk005968\" onclick=\"_$fn_check(this)\" /></td><td>2</td><td><a href=\"/005968.html\">005968</a></td><td class=\"tl\"><a href=\"/005968.html\">创金合信工业周期股票A</a></td><td class=\"xglj\"><a href=\"/005968.html\" class=\"red\">估算图</a><a href=\"http://jijinba.eastmoney.com/list,005968.html\">基金吧</a><a href=\"http://fundf10.eastmoney.com/005968.html\">档案</a></td><td><span class=\"ping\">2.3502</span></td><td>11-13</td><td><span class=\"zhang\">67.34%</span></td><td><span class=\"zhang\">111.56%</span></td><td>--</td><td>--</td><td><span class=\"lgray\">暂无评级</span></td><td><span><a class=\"zkfl\" href='http://fundf10.eastmoney.com/jjfl_005968.html'>0.15%</a></span></td><td><a class='btn dingtou' href='https://trade.1234567.com.cn/Investment/add.aspx?fc=005968'>定投</a></td></tr><tr><td><input type=\"checkbox\" value=\"005969,创金合信工业周期股票C\" id=\"chk005969\" onclick=\"_$fn_check(this)\" /></td><td>3</td><td><a href=\"/005969.html\">005969</a></td><td class=\"tl\"><a href=\"/005969.html\">创金合信工业周期股票C</a></td><td class=\"xglj\"><a href=\"/005969.html\" class=\"red\">估算图</a><a href=\"http://jijinba.eastmoney.com/list,005969.html\">基金吧</a><a href=\"http://fundf10.eastmoney.com/005969.html\">档案</a></td><td><span class=\"ping\">2.3120</span></td><td>11-13</td><td><span class=\"zhang\">66.65%</span></td><td><span class=\"zhang\">109.88%</span></td><td>--</td><td>--</td><td><span class=\"lgray\">暂无评级</span></td><td><span><a class=\"zkfl\" href='http://fundf10.eastmoney.com/jjfl_005969.html'>0.00%</a></span></td><td><a class='btn dingtou' href='https://trade.1234567.com.cn/Investment/add.aspx?fc=005969'>定投</a></td></tr></tbody></table>";
        List<List<String>> lists = parseTable(table);
        print(lists);

        List<List<String>> extract = extract(lists, COLS_NUM);
        print(extract);
    }

    private static void print(List<List<String>> lists) {
        for (List<String> list : lists) {
            System.out.println(list);
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
     * 从小括号中提取内容
     * @param string
     * @return
     */
    public static List<String> extractFromBracket(String string) {
        List<String> list = new ArrayList<>();

        Pattern p = Pattern.compile("/(?<=\\().*(?=\\))/");
        Matcher m = p.matcher(string);
        while (m.find()) {
            list.add(m.group(0).substring(1, m.group().length() - 1));
        }

        return list;
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
    private static List<List<String>> parseTable(String data) {
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
                Integer colNum = Integer.valueOf(s);
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
                colNum = Integer.valueOf(col);
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
