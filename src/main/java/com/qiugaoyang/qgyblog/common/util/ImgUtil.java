package com.qiugaoyang.qgyblog.common.util;

import java.util.UUID;

public class ImgUtil {
    /**
     * 重命名图片
     */
    public static String  upName(String filename) {
        String houzhui = filename.substring(filename.lastIndexOf("."), filename.length());

        String name = UUID.randomUUID().toString();

        return name + houzhui;

    }
}
