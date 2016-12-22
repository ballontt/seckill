package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * Created by ballontt on 2016/12/9.
 */
public interface SeckillDao {
    /**
     * @Date  2016/12/9
     * @param  seckillid
     * @return 查询返回的seckill
    */
    Seckill queryById(long seckillid);

    /**
     * @Date  2016/12/9
     * @param  off
     * @param  limit
     * @return 查询返回的seckill列表
    */
    List<Seckill> queryAll(@Param("off")int off, @Param("limit")int limit);

    /**
     * @Date  2016/12/9
     * @param seckillid
     * @param killTime
     * @return  减少的数量
    */
    int reduceNumber(@Param("seckillid") long seckillid,@Param("killTime")Date killTime);

}
