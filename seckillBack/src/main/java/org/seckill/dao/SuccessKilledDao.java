package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * Created by ballontt on 2016/12/9.
 */
public interface SuccessKilledDao {
    /**
     * 插入购买明细
     * @Date  2016/12/9
     * @param seckillid
     * @param userPhone
     * @return 返回插入的条数
    */
    int insertSuccessKilled(@Param("seckillid")long seckillid, @Param("userPhone")long userPhone);

    /**
     * 查询购买明细
     * @Date  2016/12/9
     * @param  seckillid
     * @return 返回查询的购买明细信息
    */
    SuccessKilled queryByIdWithSeckill(@Param("seckillid") long seckillid,@Param("userPhone") long userPhone);
}
