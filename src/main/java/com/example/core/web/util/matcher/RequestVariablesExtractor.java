package com.example.core.web.util.matcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface RequestVariablesExtractor {

    /**
     * 从请求中提取URL模板变量。
     * @param request  HttpServletRequest获取一个URL以从中提取变量
     * @return URL变量，如果没有找到变量则为空
     */
    Map<String, String> extractUriTemplateVariables(HttpServletRequest request);
}
