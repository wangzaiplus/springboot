package com.wangzaiplus.test.util;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {

    public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String numberChar = "0123456789";

    public static String UUID32() {
        String str = UUID.randomUUID().toString();
        return str.replaceAll("-", "");
    }

    public static String UUID36() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成包含大、小写字母、数字的字符串
     *
     * @param length
     * @return 如: zsK8rCCi
     */
    public static String generateStr(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }

    /**
     * 生成纯数字字符串
     *
     * @param length
     * @return 如: 77914
     */
    public static String generateDigitalStr(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }
        return sb.toString();
    }

    /**
     * 生成只包含大小写字母的字符串
     *
     * @param length
     * @return 如: XetrWaYc
     */
    public static String generateLetterStr(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(letterChar.charAt(random.nextInt(letterChar.length())));
        }
        return sb.toString();
    }

    /**
     * 生成只包含小写字母的字符串
     *
     * @param length
     * @return 如: nzcaunmk
     */
    public static String generateLowerStr(int length) {
        return generateLetterStr(length).toLowerCase();
    }

    /**
     * 生成只包含大写字母的字符串
     *
     * @param length
     * @return 如: KZMQXSXW
     */
    public static String generateUpperStr(int length) {
        return generateLetterStr(length).toUpperCase();
    }

    /**
     * 生成纯0字符串
     *
     * @param length
     * @return 如: 00000000
     */
    public static String generateZeroStr(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 根据数字生成字符串，长度不够前面补0
     *
     * @param num       数字
     * @param strLength 字符串长度
     * @return 如: 00000099
     */
    public static String generateStrWithZero(int num, int strLength) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (strLength - strNum.length() >= 0) {
            sb.append(generateZeroStr(strLength - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + strLength + "的字符串异常!");
        }
        sb.append(strNum);
        return sb.toString();
    }

}
