package com.shentu.lib_tools;

import com.shentu.lib_tools.constant.RegexConstants;

import java.util.regex.Pattern;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/3/11
 * @time: 11:51 AM
 * @fuction:
 */
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
        System.out.println(checkPassword("a 01786537"));
    }
}
