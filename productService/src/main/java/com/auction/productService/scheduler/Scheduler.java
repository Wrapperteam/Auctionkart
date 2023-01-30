package com.auction.productService.scheduler;

import com.auction.productService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    @Autowired
    ProductService productService;
    @Scheduled(cron = "0 0/1 * * * ?")
    public void scheduleTask()
    {
        productService.dateVerify();
         System.out.println("Cron job Scheduler: Job running at - " );
}

}


