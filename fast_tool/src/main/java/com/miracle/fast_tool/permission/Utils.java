package com.miracle.fast_tool.permission;

public class Utils {

    static void checkPermissions(final String[] permissions) {
        if (permissions == null || permissions.length == 0) {
            throw new IllegalArgumentException("permissions are null or empty");
        }
    }

    private Utils() {
        throw new AssertionError("No instances.");
    }
}
