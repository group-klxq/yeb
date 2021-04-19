package com.xxxx.server.utils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定义时间填充策略
 * 自定义设置创建或者修改的
 *      默认创建时间以及修改时间
 */
@Component
@Slf4j
public class MyTimeHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //自定义在创建新字段的时候的默认创建时间为当前时间
        this.setFieldValByName("createDate",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
