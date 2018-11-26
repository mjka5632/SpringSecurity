package com.imooc.timer;

import java.util.Arrays;
import java.util.List;

public class MyTimeTest{
    public static void main(String[] args){
//        Timer timer = new Timer();
//        MyTimeTask myTimeTask = new MyTimeTask("NO.1");
//        timer.schedule(myTimeTask,2000,1000);
// Java 8之前：
//        List features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
//        for (String feature : features) {
//            System.out.println(feature);
//        }
// Java 8之后：
        List auu = Arrays.asList("Lambdas", "Default ", "Stream ", "Date and Time API");
        List features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        features.forEach(t -> System.out.println(t));
        System.out.println("------------------");

        auu.forEach(a -> System.out.println(a));

// 使用Java 8的方法引用更方便，方法引用由::双冒号操作符标示，
// 看起来像C++的作用域解析运算符
//        features.forEach(System.out::println);
    }

}