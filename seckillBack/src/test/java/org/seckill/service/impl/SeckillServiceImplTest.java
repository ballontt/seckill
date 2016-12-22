package org.seckill.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by ballontt on 2016/12/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getById() throws Exception {

    }

    @Test
    public void getSeckillList() throws Exception {

    }

//    @Test
//    public void exportSeckillUrl() throws Exception {
//        long seckillId = 1000;
//        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
//        System.out.println(exposer);
//    }
//
//    @Test
//    public void executeSeckill() throws Exception {
//        long seckillId = 1000;
//        long userPhone = 1360218504L;
//        String md5 = "2e4e8cd214ddaa08b44837247d12d027";
//
//        try{
//            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId,userPhone,md5);
//            System.out.println(seckillExecution);
//        }catch (RepeatKillException e){
//            e.printStackTrace();
//        }catch(SeckillCloseException e1){
//            e1.printStackTrace();
//        }
//    }

    @Test
    public void testSeckillLogic() throws Exception {
        long seckillId = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);

        if(exposer.isExposed()){

            System.out.println(exposer);
            long userPhone = 136021185043L;
            String md5 = exposer.getMd5();

           try {

               SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
               System.out.println(seckillExecution);

           }catch(RepeatKillException e) {
               e.printStackTrace();

           }catch(SeckillCloseException e1) {
               e1.printStackTrace();

           }
        }else{
            //秒杀未开启
            System.out.println(exposer);
        }
    }

}