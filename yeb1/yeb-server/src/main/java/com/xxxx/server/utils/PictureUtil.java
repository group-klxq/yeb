package com.xxxx.server.utils;

import org.springframework.stereotype.Component;


/**
 * 图片的随机名
 */
@Component
public class PictureUtil {
    public static String getRandomFileName(){
        return String.valueOf(Math.random());
    }

}
