package org.seckill.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by ballontt on 2016/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml","classpath:spring/spring-web.xml"})
public class SeckillControllerTest {

    @Autowired
    private SeckillController seckillController;
    @Test
    public void list() throws Exception {
       System.out.println(seckillController.list());

    }

}