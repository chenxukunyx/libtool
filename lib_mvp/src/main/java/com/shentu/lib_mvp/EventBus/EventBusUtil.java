package com.shentu.lib_mvp.EventBus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/3/11
 * @time: 2:39 PM
 * @fuction:
 */
public class EventBusUtil {

    private EventBusUtil() {

    }

    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    public static void unregister(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }

    public static void post(EventMessage message) {
        EventBus.getDefault().post(message);
    }

    public static void postSticky(EventMessage message) {
        EventBus.getDefault().postSticky(message);
    }
}
