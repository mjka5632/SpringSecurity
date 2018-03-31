package com.imooc.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 监听队列
 * 处理返回结果
 * 初始化完成事件：ContextRefreshedEvent
 */
@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent>{

    private Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
       new Thread(()->{
           while (true){
               if(org.apache.commons.lang.StringUtils.isNotBlank(mockQueue.getCompleteOrder())){

                   String orderId=mockQueue.getCompleteOrder();
                   logger.info("返回处理结果,"+orderId);
                   deferredResultHolder.getMap().get(orderId).setResult("place order Success");
                   //防止反复处理
                   mockQueue.setCompleteOrder(null);
               }else {

                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           }
       }).start();
    }
}
