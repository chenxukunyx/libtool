package com.miracle.fast_tool.tools;


import com.miracle.fast_tool.tools.constant.RegexConstants;

import java.util.regex.Pattern;

public class RegexUtil {

    public static boolean checkMobile(String mobile) {
        return Pattern.matches(RegexConstants.REGES_MOBILE_EXACT, mobile);
    }

    public static boolean check(String regex, String target) {
        return Pattern.matches(regex, target);
    }

    public static boolean checkPassword(String password) {
        return check(RegexConstants.REGEX_PASSWORD, password);
    }

    public static void main(String[] args) {
        System.out.println(checkPassword("18501786537"));
    }
}
