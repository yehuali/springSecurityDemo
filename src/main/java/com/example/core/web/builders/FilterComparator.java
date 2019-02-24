package com.example.core.web.builders;

import javax.servlet.Filter;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * 内部仅使用{@link Comparator}对安全性{@link Filter}进行排序
 * 实例，以确保它们的顺序是正确的。
 */
final class FilterComparator implements Comparator<Filter>, Serializable {
    private static final int STEP = 100;
    private Map<String, Integer> filterToOrder = new HashMap<String, Integer>();

    FilterComparator() {
        int order = 100;
//        put(ChannelProcessingFilter.class, order);
//        order += STEP;
    }

    private void put(Class<? extends Filter> filter, int position) {
        String className = filter.getName();
        filterToOrder.put(className, position);
    }

    public int compare(Filter lhs, Filter rhs) {
        Integer left = getOrder(lhs.getClass());
        Integer right = getOrder(rhs.getClass());
        return left - right;
    }

    private Integer getOrder(Class<?> clazz) {
        while (clazz != null) {
            Integer result = filterToOrder.get(clazz.getName());
            if (result != null) {
                return result;
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }
}
