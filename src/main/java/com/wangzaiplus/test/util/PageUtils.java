package com.wangzaiplus.test.util;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class PageUtils {

    public static PageInfo merge(PageInfo pageInfo, List list) {
        PageInfo target = new PageInfo();
        BeanUtils.copyProperties(pageInfo, target);
        target.setList(list);
        return target;
    }

}
