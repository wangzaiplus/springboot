package com.wangzaiplus.test.util;

import org.apache.commons.lang3.StringUtils;

public class ViewUtils {

    private static final String VIEW_PREFIX = "/templates";
    private static final String VIEW_SUFFIX = ".html";

    private static final String ERROR = "error";
    private static final String NOT_FOUND = "404";

    private static final String TO_ERROR = "/common/error";
    private static final String TO_404 = "/common/404";

    private static final String ERROR_PAGE = VIEW_PREFIX + TO_ERROR + VIEW_SUFFIX;
    private static final String NOT_FOUND_PAGE = VIEW_PREFIX + TO_404 + VIEW_SUFFIX;

    private static final String PATH_SEPARATOR = "/";

    public static String to(String view) {
        if (StringUtils.isBlank(view) || ERROR.equals(view)) {
            return ERROR_PAGE;
        }

        if (NOT_FOUND.equals(view)) {
            return NOT_FOUND_PAGE;
        }

        if (!view.startsWith(PATH_SEPARATOR)) {
            view = PATH_SEPARATOR + view;
        }

        return VIEW_PREFIX + view + VIEW_SUFFIX;
    }

}
