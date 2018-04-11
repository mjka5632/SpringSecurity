package com.imooc.web.async;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

@RestController
public class AsyncController {
    @Autowired
    private MockQueue mockQueue;
    @Autowired
    private DeferredResultHolder deferredResultHolder;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 复杂的异步
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/ComplexOrder")
    public  DeferredResult<String> Complexorder() throws Exception {
        logger.info("主线程开始");

        String orderId= RandomStringUtils.randomNumeric(8);
        mockQueue.setPlaceOrder(orderId);
        DeferredResult<String> result = new DeferredResult<>();

        deferredResultHolder.getMap().put(orderId,result);


        return result;

    }
    /**
     * 同步
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/order")
    public String order() throws Exception {
        logger.info("主线程开始");
        //看成下单的逻辑
        Thread.sleep(1000);
        logger.info("主线返回");
        return "success";

    }

    /**
     * 异步
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/asyncOrder")
    public Callable<String> Aorder() throws Exception {
        logger.info("主线程开始");
        Callable<String> result = () -> {
            logger.info("副线程开始");
            Thread.sleep(1000);
            logger.info("副线程返回");
            return "success";
        };
        logger.info("主线返回");
        return result;

    }


}
