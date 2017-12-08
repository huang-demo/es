package com.dem.es.task;

import org.apache.http.client.utils.DateUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AutoPrintTask {

//    @Scheduled(cron = "0 */1 * * * ?")
    public void autoPrict() {
        System.out.println("------------");
        long start = System.nanoTime();
        method1();
        method2();
        method3();
        System.out.println("总耗时:" + (System.nanoTime() - start) + "纳秒");
    }


    @Async//s设置为异步方法
    public void method1() {
        long start = System.nanoTime();
        int temp = 0;
        for (int i = 0; i < 20000; i++) {
            temp += i;
        }
        System.out.println("方法1耗时:" + (System.nanoTime() - start) + "纳秒");
    }

    @Async
    public void method2() {
        long start = System.nanoTime();
        int temp = 0;
        for (int i = 0; i < 10000; i++) {
            temp += i;
        }
        System.out.println("方法2耗时:" + (System.nanoTime() - start) + "纳秒");
    }

    @Async
    public void method3() {
        long start = System.nanoTime();
        int temp = 0;
        for (int i = 0; i < 10000; i++) {
            temp += i;
        }
        System.out.println("方法3耗时:" + (System.nanoTime() - start) + "纳秒");
    }
}
