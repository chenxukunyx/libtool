package com.shentu.lib_view.banner;


public interface BannerConfig {
    /*
     * indicator style
     */
    /**
     * 不显示指示器和标题
     */
    int NOT_INDICATOR = 0;
    /**
     * 显示圆形指示器
     */
    int CIRCLE_INDICATOR = 1;
    /**
     * 显示数字指示器
     */
    int NUM_INDICATOR = 2;
    /**
     * 显示数字指示器和标题
     */
    int NUM_INDICATOR_TITLE = 3;
    /**
     * 显示圆形指示器和标题（垂直显示）
     */
    int CIRCLE_INDICATOR_TITLE = 4;
    /**
     * 显示圆形指示器和标题（水平显示）
     */
    int CIRCLE_INDICATOR_TITLE_INSIDE = 5;

    /*
     * indicator gravity
     */
    /**
     * 指示器居左
     */
    int LEFT = 5;
    /**
     * 指示器居中
     */
    int CENTER = 6;
    /**
     * 指示器居右
     */
    int RIGHT = 7;

    /**
     * banner
     */
    int PADDING_SIZE = 5;
    int TIME = 2000;
    int DURATION = 800;
    boolean IS_AUTO_PLAY = true;
    boolean IS_SCROLL = true;

    /**
     * title style
     */
    int TITLE_BACKGROUND = -1;
    int TITLE_HEIGHT = -1;
    int TITLE_TEXT_COLOR = -1;
    int TITLE_TEXT_SIZE = -1;
}
