package com.shentu.lib_image.options;

import java.util.LinkedHashMap;
import java.util.Map;


public abstract class DisplayOptionFactory<T> {

    private final static String defaultOptionName = DisplayOptionFactory.class.getName();
    private Map<String, DisplayOption> mDisplayOptions = new LinkedHashMap<>();
    private Map<String, T> mTargetOptions = new LinkedHashMap<>();

    /**
     * 获取默认option在factory中的键值名字
     *
     * return
     */
    public static String getDefaultOptionName() {
        return defaultOptionName;
    }

    public abstract T toTargetOptions(DisplayOption op);

    /**
     * 增加一个圆角为radius的option，其他参数用默认值
     *
     * param name     option名字
     * param radius   圆角像素直径
     * param position 圆角位置
     */
    public DisplayOptionFactory add(String name, int radius, int position) {
        DisplayOption op = new DisplayOption().radius(radius).radiusPosition(position);
        mDisplayOptions.put(name, op);
        mTargetOptions.put(name, toTargetOptions(op));
        return this;
    }

    /**
     * 增加一个圆角为radius，对齐方式为fitop的option，其他参数默认
     *
     * param name
     * param fitop
     * param radius
     * param position
     */
    public DisplayOptionFactory add(String name, DisplayOption.FitOption fitop, int radius, int position) {
        DisplayOption op = new DisplayOption().fitOption(fitop).radius(radius).radiusPosition(position);
        mDisplayOptions.put(name, op);
        mTargetOptions.put(name, toTargetOptions(op));
        return this;
    }

    public DisplayOptionFactory add(String name, DisplayOption op) {
        mDisplayOptions.put(name, op);
        mTargetOptions.put(name, toTargetOptions(op));
        return this;
    }

    public DisplayOptionFactory remove(String name) {
        mDisplayOptions.remove(name);
        mTargetOptions.remove(name);
        return this;
    }

    public DisplayOption get(String name) {
        return mDisplayOptions.get(name);
    }

    public T getTarget(String name) {
        return mTargetOptions.get(name);
    }

    public T getTarget(DisplayOption op) {
        for (Map.Entry entry : mDisplayOptions.entrySet()) {
            if (entry.getValue().equals(op)) {
                return mTargetOptions.get(entry.getKey());
            }
        }
        return null;
    }
}

