package com.github.gtn1024.sa.utils;

import com.github.gtn1024.sa.config.SuperAdminConfig;

public class SuperAdminUtils {
    public static boolean isSa(long id) {
        return SuperAdminConfig.INSTANCE.getSa() == id;
    }
}