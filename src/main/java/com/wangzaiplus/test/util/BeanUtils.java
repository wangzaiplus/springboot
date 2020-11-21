package com.wangzaiplus.test.util;

import com.wangzaiplus.test.annotation.ColNum;
import com.wangzaiplus.test.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class BeanUtils {

    private static final String TYPE_STRING = "String";
    private static final String TYPE_DATE = "Date";
    private static final String TYPE_INT = "int";
    private static final String TYPE_INTEGER = "Integer";
    private static final String TYPE_LONG = "Long";
    private static final String TYPE_DOUBLE = "Double";
    private static final String TYPE_BOOLEAN = "Boolean";
    private static final String TYPE_BIG_DECIMAL = "BigDecimal";

    public static void convert(List<String> list, Object o) throws Exception {
        if (CollectionUtils.isEmpty(list) || null == o) {
            throw new ServiceException("converter param error");
        }

        Class<?> clazz = o.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ColNum colNum = field.getAnnotation(ColNum.class);
            if (null == colNum) {
                continue;
            }

            int num = colNum.colNum();
            if (num < 1 || num > list.size()) {
                throw new ServiceException("colNum error: " + num);
            }

            String value = list.get(num - 1);
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
            Method writeMethod = pd.getWriteMethod();
            if (null == writeMethod || StringUtils.isBlank(value)) {
                continue;
            }

            String fieldType = field.getType().getSimpleName();
            Object parsedValue = parseValue(fieldType, value);
            writeMethod.invoke(o, parsedValue);
        }
    }

    private static Object parseValue(String fieldType, String value) {
        if (TYPE_STRING.equals(fieldType)) {
            return value;
        } else if (TYPE_DATE.equals(fieldType)) {
            return JodaTimeUtil.strToDate(value);
        } else if (TYPE_INT.equals(fieldType) || TYPE_INTEGER.equals(value)) {
            return Integer.parseInt(value);
        } else if (TYPE_LONG.equalsIgnoreCase(fieldType)) {
            return Long.parseLong(value);
        } else if (TYPE_DOUBLE.equalsIgnoreCase(fieldType)) {
            return Double.parseDouble(value);
        } else if (TYPE_BOOLEAN.equalsIgnoreCase(fieldType)) {
            return Boolean.TRUE.toString().equalsIgnoreCase(fieldType);
        } else if (TYPE_BIG_DECIMAL.equalsIgnoreCase(fieldType)) {
            return new BigDecimal(value);
        } else {
            throw new ServiceException("fieldType error: "+ fieldType);
        }
    }

}
