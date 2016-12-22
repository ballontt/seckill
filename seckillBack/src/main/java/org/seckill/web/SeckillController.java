package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by ballontt on 2016/12/14.
 */
@Controller
@RequestMapping("/seckill") //url: /模块/资源/{id}/细分
public class SeckillController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value="/list",method = RequestMethod.GET,produces={"application/json;charset=utf-8"})
    @ResponseBody
    //public String list(Model model) {
     public List<Seckill>list() {
        //获取列表页
        List<Seckill> seckillList = seckillService.getSeckillList();
        return seckillList;
        //model.addAttribute("seckillList",seckillList);

        //list.jsp + model = ModelAndView
        //return "list";
    }

    @RequestMapping(value="/{seckillId}/detail",method=RequestMethod.GET,produces={"application/json;charset=utf8"})
    @ResponseBody
    public Seckill detail(@PathVariable("seckillId") Long seckillId,Model model) {
        Seckill seckill = seckillService.getById(seckillId);
        return seckill;
    }

    @RequestMapping(value = "/{seckillId}/exposer",
            method=RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") long seckillId)  {

        SeckillResult<Exposer> result;
        try{
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }

    @RequestMapping(value="/{seckillId}/{md5}/{phoneCookie}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @PathVariable("phoneCookie") Long userPhone){

        if(userPhone == null) {
            return new SeckillResult<SeckillExecution>(false,"未注册");
        }
        SeckillResult<SeckillExecution> seckillResult;
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
            return seckillResult = new SeckillResult<SeckillExecution>(true, seckillExecution);
        }catch (RepeatKillException e1) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStatEnum.REPEAT_KILL);
            return seckillResult = new SeckillResult<SeckillExecution>(false,seckillExecution);
        }catch(SeckillCloseException e2) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStatEnum.END);
            return seckillResult = new SeckillResult<SeckillExecution>(false,seckillExecution);
        }
        catch(Exception e){
            logger.error(e.getMessage(),e);
            SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStatEnum.INNER_ERROR);
            return seckillResult = new SeckillResult<SeckillExecution>(false,seckillExecution);
        }
    }

    @RequestMapping(value="/time/now",method=RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time() {
        Date nowTime = new Date();
        return  new SeckillResult<Long>(true,nowTime.getTime());
    }
}
