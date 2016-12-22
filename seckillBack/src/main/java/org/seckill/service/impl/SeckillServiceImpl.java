package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by ballontt on 2016/12/13.
 */
/**
 * 1.开发团队达成一致约定，明确标注事务方法的编程风格
 * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部
 * 3.不是所有的方法都需要事务，如只有一条修改操作、只读操作不要事务控制
 */
@Service
@Transactional
public class SeckillServiceImpl implements SeckillService {
    //日志对象
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String salt = "ballontt4sfa.2aj";

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);

        //查不到该产品记录
        if(seckill == null) {
            return new Exposer(false,seckillId);
        }

        //秒杀未开启
        Date endTime = seckill.getEndTime();
        Date startTime = seckill.getStartTime();
        Date nowTime = new Date();
        //返回当前时间
        if(endTime.getTime() < nowTime.getTime() || startTime.getTime() > nowTime.getTime()) {
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        //秒杀开启，返回seckillID和给接口加密的md5
        else{
            String md5 = getMD5(seckillId);
            return new Exposer(true,seckillId,md5);
        }
    }
    //生成MD5值
    private String getMD5(long id) {
        String base = id + "/" +salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    //秒杀执行，成功：减库存，增加明细。失败：抛出异常
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {

        //根据md5值判断是否是正确的
        if(md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }

        //执行秒杀逻辑：减库存+增加明细
        Date nowTime = new Date();
        try{
            //减库存
            int updateCount = seckillDao.reduceNumber(seckillId,nowTime);
            //如果没有更新成功说明秒杀结束
            if(updateCount == 0) {
                throw new SeckillCloseException("seckill is closed");
            }
            else{
                //增加明细
                int insertCount =  successKilledDao.insertSuccessKilled(seckillId,userPhone);
                //如果没有增加成功，说明是重复秒杀，抛出异常结束
                if(insertCount <= 0) {
                    throw new RepeatKillException("seckill repeated");
                }
                else{
                    //更新明细,返回执行结果，执行结果是一个实体，里面记录了seckillid，秒杀明细，秒杀状态
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS,successKilled);
                }
        }
    }catch (SeckillCloseException e1){
        throw e1;
    }catch(RepeatKillException e2) {
        throw e2;
    }catch (Exception e) {
        logger.error(e.getMessage(),e);
            //所有编译器异常转换
        throw new SeckillException("seckill inner error:"+e.getMessage());
        }
    }
}
