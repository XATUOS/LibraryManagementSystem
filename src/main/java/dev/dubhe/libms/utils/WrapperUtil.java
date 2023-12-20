package dev.dubhe.libms.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Collection;

@SuppressWarnings("unused")
public class WrapperUtil {

    public static <T> QueryWrapper<T> eq(
            String name, Object value
    ) {
        return new QueryWrapper<T>()
                .eq(name, value);
    }

    public static <T> QueryWrapper<T> eq(
            String name1, Object value1,
            String name2, Object value2
    ) {
        return new QueryWrapper<T>()
                .eq(name1, value1)
                .eq(name2, value2);
    }

    public static <T> QueryWrapper<T> eq(
            String name1, Object value1,
            String name2, Object value2,
            String name3, Object value3
    ) {
        return new QueryWrapper<T>()
                .eq(name1, value1)
                .eq(name2, value2)
                .eq(name3, value3);
    }

    public static <T> QueryWrapper<T> eq(
            String name1, Object value1,
            String name2, Object value2,
            String name3, Object value3,
            String name4, Object value4

    ) {
        return new QueryWrapper<T>()
                .eq(name1, value1)
                .eq(name2, value2)
                .eq(name3, value3)
                .eq(name4, value4);
    }

    public static <T> QueryWrapper<T> oneEq(
            String name, Object value
    ) {
        return new QueryWrapper<T>()
                .eq(name, value)
                .last("limit 1");
    }

    public static <T> QueryWrapper<T> oneEq(
            String name1, Object value1,
            String name2, Object value2
    ) {
        return new QueryWrapper<T>()
                .eq(name1, value1)
                .eq(name2, value2)
                .last("limit 1");
    }

    public static <T> QueryWrapper<T> eqLike(
            String eqName, Object eqValue,
            String likeName, String likeValue
    ) {
        return new QueryWrapper<T>()
                .eq(eqName, eqValue)
                .like(likeName, likeValue);
    }

    public static <T> QueryWrapper<T> in(
            String name, Collection<?> list
    ) {
        return new QueryWrapper<T>()
                .in(name, list);
    }
}
