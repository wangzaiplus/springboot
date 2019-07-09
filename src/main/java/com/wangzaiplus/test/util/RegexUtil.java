package com.wangzaiplus.test.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class RegexUtil {

    // 正则表达式: 验证手机号
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
    // 验证邮箱
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    // 验证汉字
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";
    // 验证身份证
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
    // 验证URL
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    // 验证IP地址
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 手机号
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        return StringUtils.isNotBlank(mobile) && Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        return StringUtils.isNotBlank(email) && Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 汉字
     *
     * @param chinese
     * @return
     */
    public static boolean isChinese(String chinese) {
        return StringUtils.isNotBlank(chinese) && Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 身份证
     *
     * @param idCard
     * @return
     */
    public static boolean isIDCard(String idCard) {
        return StringUtils.isNotBlank(idCard) && Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * URL
     *
     * @param url
     * @return
     */
    public static boolean isUrl(String url) {
        return StringUtils.isNotBlank(url) && Pattern.matches(REGEX_URL, url);
    }

    /**
     * IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return StringUtils.isNotBlank(ipAddr) && Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

}
