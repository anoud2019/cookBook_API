package com.cookBook.cookbook_api.Utils;

import java.util.List;

public class HelperUtils {
    public static <F> boolean isNull(F value) {
        return value == null;
    }

    public static <A> boolean isNotNull(A value) {
        return value != null;
    }
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    public static <T> boolean isNotEmpty(List<T> list) {
        return list != null && !list.isEmpty();
    }

}
