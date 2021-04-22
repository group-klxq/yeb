package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.MenuRole;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shi
 * @since 2021-04-16
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    //批量更新
    Integer insertRecord(Integer rid, Integer[] mids);
    Integer insertRecord(Integer rid, Integer[] mids);
}
